package com.vrsalex.subscription.dto.model

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.SubscriptionPlan
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID


data class CreateSubscriptionRequest(
    val userId: UUID,
    val serviceType: ServiceType,
    val plan: SubscriptionPlan,
    val price: BigDecimal,
    val startDate: LocalDate,
    val endDate: LocalDate,
)