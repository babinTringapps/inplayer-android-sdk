package com.s.domain.usecase.autehntication

import com.s.domain.entity.GrantType
import com.s.domain.entity.InPlayerDomainUser
import com.s.domain.gateway.InPlayerAccountRepository
import com.s.domain.schedulers.MySchedulers
import com.s.domain.usecase.base.SingleUseCase
import io.reactivex.Single

/**
 * Created by victor on 12/20/18
 */
class AuthenticateUserUseCase constructor(schedulers: MySchedulers,
                                          private val inPlayerAuthenticatorRepository: InPlayerAccountRepository)
    : SingleUseCase<InPlayerDomainUser, AuthenticateUserUseCase.Params>(schedulers) {
    
    override fun buildUseCaseObservable(params: Params?): Single<InPlayerDomainUser> {
        
        params?.let { params ->
            
            if (params.grantType == GrantType.PASSWORD) {
                
                params.username?.let { username ->
                    params.password?.let { password ->
                        return inPlayerAuthenticatorRepository.autehenticate(username, password, params.grantType.toString(), params.clientId)
                    }
                    throw IllegalStateException("Password can't be null for GrantType Password!")
                }
                throw IllegalStateException("Username can't be null for GrantType Password!")
                
            } else if (params.grantType == GrantType.REFRESH_TOKEN) {
                
                params.refreshToken?.let {
                    return inPlayerAuthenticatorRepository.refreshToken(params.refreshToken, params.grantType.toString(), params.clientId)
                }
                throw IllegalStateException("Refresh Token can't be null for GrantType REFRESH_TOKEN!")
                
            } else if (params.grantType == GrantType.CLIENT_CREDENTIALS) {
                
                params.clientSecret?.let {
                    return inPlayerAuthenticatorRepository.clientCredentialsAuthentication(it, params.grantType.toString(), params.clientId)
                }
                throw IllegalStateException("Client Secret can't be null for GrantType CLIENT_CREDENTIALS!")
                
            } else
                throw IllegalStateException("Unsupported GrantType ${params.grantType}")
            
        }
        
        throw IllegalStateException("Params Can't be null for AuthenticateUserUseCase")
    }
    
    
    data class Params(val username: String? = null, val password: String? = null, val grantType: GrantType, val clientId: String, val clientSecret: String? = null, val refreshToken: String? = null)
    
}