package com.vrsalex.subscription.domain

import java.time.Instant
import java.util.UUID

data class SubscriptionStatusHistory(
    val id: UUID,
    val subscriptionId: UUID,
    val oldStatus: SubscriptionStatus?,
    val newStatus: SubscriptionStatus,
    val timestamp: Instant,
    val message: String?
)