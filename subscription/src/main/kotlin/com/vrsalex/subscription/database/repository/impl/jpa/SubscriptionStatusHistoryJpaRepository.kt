package com.vrsalex.subscription.database.repository.impl.jpa

import com.vrsalex.subscription.database.entity.SubscriptionStatusHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID


interface SubscriptionStatusHistoryJpaRepository : JpaRepository<SubscriptionStatusHistoryEntity, UUID> {

    fun findAllBySubscriptionIdOrderByTimestampAsc(
        subscriptionId: UUID
    ): List<SubscriptionStatusHistoryEntity>

}