package com.vrsalex.subscription.dto.model

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.SubscriptionPlan
import com.vrsalex.subscription.domain.SubscriptionStatus
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID


data class SubscriptionResponse(
    val id: UUID,
    val userId: UUID,
    val serviceType: ServiceType,
    val plan: SubscriptionPlan,
    val status: SubscriptionStatus,
    val price: BigDecimal,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: Instant,
    val updatedAt: Instant
)