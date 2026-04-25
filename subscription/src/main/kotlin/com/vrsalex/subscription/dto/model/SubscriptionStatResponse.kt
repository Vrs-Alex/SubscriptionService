package com.vrsalex.subscription.dto.model


data class SubscriptionStatResponse(
    val total: Long,
    val active: Long,
    val paused: Long,
    val cancelled: Long,
    val expired: Long
)