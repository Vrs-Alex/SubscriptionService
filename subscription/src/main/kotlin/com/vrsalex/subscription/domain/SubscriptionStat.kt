package com.vrsalex.subscription.domain

data class SubscriptionStat(
    val total: Long,
    val active: Long,
    val paused: Long,
    val cancelled: Long,
    val expired: Long
)