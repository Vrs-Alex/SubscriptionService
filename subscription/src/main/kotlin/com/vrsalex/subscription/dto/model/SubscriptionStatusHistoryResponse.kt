package com.vrsalex.subscription.dto.model

import com.vrsalex.subscription.domain.SubscriptionStatus
import java.time.Instant
import java.util.UUID


data class SubscriptionStatusHistoryResponse(
    val id: UUID,
    val subscriptionId: UUID,
    val oldStatus: SubscriptionStatus?,
    val newStatus: SubscriptionStatus,
    val timestamp: Instant,
    val message: String?
)