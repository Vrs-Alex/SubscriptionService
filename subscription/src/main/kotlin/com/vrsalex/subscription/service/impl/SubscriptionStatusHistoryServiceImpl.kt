package com.vrsalex.subscription.service.impl

import com.vrsalex.subscription.database.repository.SubscriptionStatusHistoryRepository
import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionStatus
import com.vrsalex.subscription.domain.SubscriptionStatusHistory
import com.vrsalex.subscription.service.SubscriptionStatusHistoryService
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID


@Service
class SubscriptionStatusHistoryServiceImpl(
    private val repository: SubscriptionStatusHistoryRepository
) : SubscriptionStatusHistoryService {

    override fun save(
        subscription: Subscription,
        oldStatus: SubscriptionStatus?,
        message: String?
    ): SubscriptionStatusHistory =
        repository.save(
            SubscriptionStatusHistory(
                id = UUID.randomUUID(),
                subscriptionId = subscription.id,
                oldStatus = oldStatus,
                newStatus = subscription.status,
                timestamp = Instant.now(),
                message = message
            )
        )

    override fun findAllBySubscriptionId(
        subscriptionId: UUID
    ): List<SubscriptionStatusHistory> =
        repository.findAllBySubscriptionId(subscriptionId)

}