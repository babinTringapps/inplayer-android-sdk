package com.sdk.domain.entity.subscribtion

data class SubscriptionEntity(
        val amount: Int,
        val asset_id: Int,
        val asset_title: String,
        val cancel_token: String,
        val created_at: Int,
        val currency: String,
        val description: String,
        val formatted_amount: String,
        val merchant_id: Int,
        val next_billing_date: Int,
        val status: String,
        val unsubscribe_url: String,
        val updated_at: Int
)