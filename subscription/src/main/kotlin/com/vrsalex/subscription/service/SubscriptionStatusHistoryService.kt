package com.vrsalex.subscription.service

import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionStatus
import com.vrsalex.subscription.domain.SubscriptionStatusHistory
import java.util.UUID


interface SubscriptionStatusHistoryService {

    fun save(
        subscription: Subscription,
        oldStatus: SubscriptionStatus?,
        message: String?
    ): SubscriptionStatusHistory

    fun findAllBySubscriptionId(subscriptionId: UUID): List<SubscriptionStatusHistory>

}