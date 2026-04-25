package com.vrsalex.subscription.database.repository

import com.vrsalex.subscription.domain.SubscriptionStatusHistory
import java.util.UUID


interface SubscriptionStatusHistoryRepository {

    fun save(history: SubscriptionStatusHistory): SubscriptionStatusHistory

    fun findAllBySubscriptionId(subscriptionId: UUID): List<SubscriptionStatusHistory>
}