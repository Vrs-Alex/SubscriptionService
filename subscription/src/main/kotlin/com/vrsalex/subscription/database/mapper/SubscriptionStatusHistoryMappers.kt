package com.vrsalex.subscription.database.mapper

import com.vrsalex.subscription.database.entity.SubscriptionEntity
import com.vrsalex.subscription.database.entity.SubscriptionStatusHistoryEntity
import com.vrsalex.subscription.domain.SubscriptionStatusHistory
import java.time.ZoneOffset


fun SubscriptionStatusHistory.toEntity(
    subscriptionEntity: SubscriptionEntity
): SubscriptionStatusHistoryEntity = SubscriptionStatusHistoryEntity().apply {
    id = this@toEntity.id
    subscription = subscriptionEntity
    oldStatus = this@toEntity.oldStatus
    newStatus = this@toEntity.newStatus
    timestamp = this@toEntity.timestamp.atOffset(ZoneOffset.UTC)
    message = this@toEntity.message
}

fun SubscriptionStatusHistoryEntity.toDomain(): SubscriptionStatusHistory =
    SubscriptionStatusHistory(
        id = id!!,
        subscriptionId = subscription!!.id!!,
        oldStatus = oldStatus,
        newStatus = newStatus,
        timestamp = timestamp.toInstant(),
        message = message
    )