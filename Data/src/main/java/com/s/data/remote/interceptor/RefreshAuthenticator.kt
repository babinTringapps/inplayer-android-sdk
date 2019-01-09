package com.s.data.remote.interceptor

import android.util.Log
import com.s.data.Constants
import com.s.data.remote.refresh_token.InPlayerRemoteRefreshServiceAPI
import com.s.data.repository.gateway.UserLocalAuthenticator
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Created by victor on 1/3/19
 */
class RefreshAuthenticator constructor(val localAuthenticator: UserLocalAuthenticator,
                                       val inPlayerRemoteRefreshServiceAPI: InPlayerRemoteRefreshServiceAPI) : Authenticator {
    
    init {
        Log.v("InPlayerRemoteRefresh", "Init")
    }
    
    val MAX_RETRY = 3
    
    val TAG = "RefreshAuthenticator"
    
    override fun authenticate(route: Route, response: Response): Request? {
        Log.d(TAG, "Detected authentication error ${response.code()} on ${response.request()?.url()}")
        
        when (hasBearerAuthorizationToken(response)) {
            false -> {
                // No bearer auth token; nothing to refresh!
                Log.d(TAG, "No bearer authentication to refresh.")
                return null
            }
            true -> {
                // It had a bearer auth!
                Log.d(TAG, "Bearer authentication present!")
                val previousRetryCount = retryCount(response)
                // Attempt to reauthenticate using the refresh token!
                return reAuthenticateRequestUsingRefreshToken(
                        response,
                        previousRetryCount + 1
                )
            }
        }
    }
    
    private fun retryCount(response: Response?): Int {
        return response?.request()?.header(Constants.HttpHeaderRetryCount)?.toInt() ?: 0
    }
    
    private fun hasBearerAuthorizationToken(response: Response?): Boolean {
        response?.let { response ->
            val authorizationHeader = response.request().header(Constants.HttpHeaderAuthorization)
            return authorizationHeader.startsWith(Constants.HttpHeaderBearerTokenPrefix)
        }
        return false
    }
    
    private fun isBearerAuthorizationTokenDifferent(response: Response?): Boolean {
        response?.let { response ->
            val authorizationHeader = response.request().header(Constants.HttpHeaderAuthorization)
            return authorizationHeader == localAuthenticator.getBearerAuthToken()
        }
        return false
    }
    
    // We synchronize this request, so that multiple concurrent failures
    // don't all try to use the same refresh token!
    @Synchronized
    private fun reAuthenticateRequestUsingRefreshToken(staleRequest: Response, retryCount: Int): Request? {
        
        // See if we have gone too far:
        if (retryCount > MAX_RETRY) {
            // Yup!
            Log.d(TAG, "Retry count exceeded! Giving up.")
            // Don't try to re-authenticate any more.
            return null
        }
        
        // We have some retries left!
        Log.d(TAG, "Attempting to fetch a new token...")
        
        // Could not retrieve new token! Unable to re-authenticate!
        if (localAuthenticator.getRefreshToken().isEmpty()) {
            Log.d(TAG, "Failed to retrieve new token, unable to re-authenticate!")
            return null
        }
        
        if (isBearerAuthorizationTokenDifferent(staleRequest))
            makeRefreshTokenRequest()
        
        // Try for the new token:
        Log.d(TAG, "Retreived new token, re-authenticating...")
        return rewriteRequest(staleRequest.request(), retryCount, localAuthenticator.getBearerAuthToken())
        
    }
    
    private fun makeRefreshTokenRequest() {
        Log.d(TAG, "Creating new Refresh Token Request")
        
        val refreshTokenCall = inPlayerRemoteRefreshServiceAPI.authenticate(localAuthenticator.getRefreshToken(), "refresh_token", "3b39b5ab-b5fc-4ba3-b770-73155d20e61f")
        
        val request = refreshTokenCall.execute()
        
        val authorizationModelResponse = request.body()
        
        if (authorizationModelResponse != null) {
            Log.d(TAG, "Creating new Refresh SUCCESS!! $authorizationModelResponse")
            localAuthenticator.saveAuthenticationToken(authorizationModelResponse.accessToken)
            authorizationModelResponse.refreshToken?.let {
                localAuthenticator.saveRefreshToken(it)
            }
        } else
            Log.d(TAG, "Creating new Refresh FAILED!!")
    }
    
    private fun rewriteRequest(
            staleRequest: Request?, retryCount: Int, authToken: String
    ): Request? {
        return staleRequest?.newBuilder()
                ?.header(
                        Constants.HttpHeaderAuthorization,
                        authToken
                )
                ?.header(
                        Constants.HttpHeaderRetryCount,
                        "$retryCount"
                )
                ?.build()
    }
    
}