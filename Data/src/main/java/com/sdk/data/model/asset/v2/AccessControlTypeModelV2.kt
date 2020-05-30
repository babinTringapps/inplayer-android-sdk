package com.sdk.data.model.asset.v2

import com.sdk.domain.entity.asset.AccessControlTypeEntity

/**
 * Same class as [AccessControlTypeModel] but auth field is int instead of Boolean
 * */
data class AccessControlTypeModelV2(val id: Long, val name: String, val auth: Int) {
    fun mapToEntity(): AccessControlTypeEntity {
        return AccessControlTypeEntity(
            id,
            name,
            auth == 1
        )
    }
}