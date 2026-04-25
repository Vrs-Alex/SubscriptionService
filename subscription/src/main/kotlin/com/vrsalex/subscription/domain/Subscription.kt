package com.vrsalex.subscription.domain

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class Subscription(
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
) {
    fun transitionTo(next: SubscriptionStatus): Subscription {
        require(status.canTransitionTo(next)) {
            "Недопустимый переход: $status → $next"
        }
        return copy(status = next, updatedAt = Instant.now())
    }

    fun renew(newEndDate: LocalDate): Subscription {
        require(newEndDate.isAfter(LocalDate.now())) {
            "Дата окончания должна быть в будущем"
        }
        return copy(
            status = SubscriptionStatus.ACTIVE,
            endDate = newEndDate,
            updatedAt = Instant.now()
        )
    }

    fun isExpired(): Boolean = endDate.isBefore(LocalDate.now())
}


data class SubscriptionCreate(
    val userId: UUID,
    val serviceType: ServiceType,
    val plan: SubscriptionPlan,
    val price: BigDecimal,
    val startDate: LocalDate,
    val endDate: LocalDate
)