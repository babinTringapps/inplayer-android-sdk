package com.sdk.data.remote.api

import com.sdk.data.model.ResponseModel
import com.sdk.data.model.account.InPlayerAuthorizationModel
import com.sdk.data.model.asset.AccessFeeModel
import com.sdk.data.model.asset.ItemDetailsModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface InPlayerRemotePublicServiceAPI {
    
    @FormUrlEncoded
    @POST("/accounts")
    fun createAccount(
        @Field("full_name") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("type") type: String,
        @Field("merchant_uuid") merchantUUID: String,
        @Field("referrer") referrer: String?,
        @FieldMap metadata: HashMap<String, String>?
    ): Single<InPlayerAuthorizationModel>
    
    
    @FormUrlEncoded
    @POST("/accounts/authenticate")
    fun authenticate(
        @Field("username") username: String? = null,
        @Field("password") password: String? = null,
        @Field("client_secret") clientSecret: String? = null,
        @Field("refresh_token") refreshToken: String? = null,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String
    ): Single<InPlayerAuthorizationModel>
    
    
    @FormUrlEncoded
    @POST("/accounts/forgot-password")
    fun forgotPassword(
        @Field("merchant_uuid") merchantUUID: String,
        @Field("email") email: String
    ): Single<ResponseModel>
    
    
    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/accounts/forgot-password/{token}", hasBody = true)
    fun setNewPassword(
        @Path("token") token: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): Single<Response<Void>>
    
    @GET("/items/{merchant_uuid}/{id}")
    fun getItemDetails(@Path("id") id: Int, @Path("merchant_uuid") merchantUUID: String): Single<ItemDetailsModel>
    
    @GET("/items/assets/external/{asset_type}/{external_asset_id}")
    fun getExternalAsset(
        @Path("asset_type") assetType: String, @Path("external_asset_id") externalId: String, @Query(
            "merchant_uuid"
        ) merchantUUID: String
    ): Single<ItemDetailsModel>
    
    @GET("/items/{id}/access-fees")
    fun getAccessFees(@Path("id") id: Int): Single<List<AccessFeeModel>>
}