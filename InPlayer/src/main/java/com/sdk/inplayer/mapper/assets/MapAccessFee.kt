package com.sdk.inplayer.mapper.assets

import com.sdk.domain.entity.asset.AccessFeeEntity
import com.sdk.domain.entity.mapper.DomainMapper
import com.sdk.inplayer.model.assets.*

internal class MapAccessFee constructor(
    private val mapAccessType: MapAccessType,
    private val mapTrialPeriod: MapTrialPeriod,
    private val mapSetupFee: MapSetupFee
) : DomainMapper<AccessFeeEntity, InPlayerAccessFee> {
    
    override fun mapFromDomain(domainEntity: AccessFeeEntity): InPlayerAccessFee {
        return InPlayerAccessFee(
            id = domainEntity.id,
            merchantId = domainEntity.merchantId,
            amount = domainEntity.amount,
            currency = domainEntity.currency,
            description = domainEntity.description,
            accessType = domainEntity.accessTypeEntity?.let { mapAccessType.mapFromDomain(it) },
            itemType = domainEntity.itemType,
            trialPeriod = domainEntity.trialPeriodEntity?.let { mapTrialPeriod.mapFromDomain(it) },
            setupFee = domainEntity.setupFeeEntity?.let { mapSetupFee.mapFromDomain(it) },
            expiresAt = domainEntity.expiresAt,
            startsAt = domainEntity.startsAt,
            createdAt = domainEntity.createdAt,
            updatedAt = domainEntity.updatedAt,
            geoRestriction = domainEntity.geoRestrictionEntity?.let { InPlayerGeoRestriction(it) },
            seasonalFee = domainEntity.seasonalFeeEntity?.let { InPlayerSeasonalFee(it) },
            externalFees = domainEntity.externalFeesEntity?.map { InPlayerExternalFees(it) },
            item = domainEntity.item?.let { InPlayerItemAccess(it) },
            voucherRule = domainEntity.voucherRule?.let { InPlayerVoucherRule(it) }
        )
    }
    
    override fun mapToDomain(viewModel: InPlayerAccessFee): AccessFeeEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}