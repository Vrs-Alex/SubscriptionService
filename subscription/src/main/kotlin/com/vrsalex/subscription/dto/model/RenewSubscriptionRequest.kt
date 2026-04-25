package com.vrsalex.subscription.dto.model

import kotlinx.serialization.Serializable
import java.time.LocalDate


data class RenewSubscriptionRequest(
    val newEndDate: LocalDate
)