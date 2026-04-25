package com.vrsalex.subscription.database.repository.impl

import com.vrsalex.subscription.database.mapper.toDomain
import com.vrsalex.subscription.database.mapper.toEntity
import com.vrsalex.subscription.database.repository.SubscriptionStatusHistoryRepository
import com.vrsalex.subscription.database.repository.impl.jpa.SubscriptionJpaRepository
import com.vrsalex.subscription.database.repository.impl.jpa.SubscriptionStatusHistoryJpaRepository
import com.vrsalex.subscription.domain.SubscriptionStatusHistory
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
class SubscriptionStatusHistoryRepositoryImpl(
    private val jpa: SubscriptionStatusHistoryJpaRepository,
    private val subscriptionJpa: SubscriptionJpaRepository
) : SubscriptionStatusHistoryRepository {

    override fun save(history: SubscriptionStatusHistory): SubscriptionStatusHistory {
        val subscriptionEntity = subscriptionJpa.findById(history.subscriptionId)
            .orElseThrow { NoSuchElementException("Подписка не найдена: ${history.subscriptionId}") }
        return jpa.save(history.toEntity(subscriptionEntity)).toDomain()
    }

    override fun findAllBySubscriptionId(
        subscriptionId: UUID
    ): List<SubscriptionStatusHistory> =
        jpa.findAllBySubscriptionIdOrderByTimestampAsc(subscriptionId)
            .map { it.toDomain() }

}