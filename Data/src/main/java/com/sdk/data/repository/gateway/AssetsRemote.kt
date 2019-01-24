package com.sdk.data.repository.gateway

import com.sdk.data.model.asset.AccessFeeModel
import com.sdk.data.model.asset.ItemAccessModel
import com.sdk.data.model.asset.ItemDetailsModel
import io.reactivex.Single


interface AssetsRemote {
    
    fun getItemAccess(id: Int): Single<ItemAccessModel>
    
    fun getItemDetails(id: Int, merchantUUID: String): Single<ItemDetailsModel>
    
    fun getAccessFees(id: Int): Single<List<AccessFeeModel>>
}