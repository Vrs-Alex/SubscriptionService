package com.vrsalex.subscription.database.mapper

import com.vrsalex.subscription.database.entity.SubscriptionEntity
import com.vrsalex.subscription.domain.Subscription
import java.time.ZoneOffset


fun Subscription.toEntity(): SubscriptionEntity = SubscriptionEntity().apply {
    id = this@toEntity.id
    userId = this@toEntity.userId
    serviceType = this@toEntity.serviceType
    plan = this@toEntity.plan
    status = this@toEntity.status
    price = this@toEntity.price
    startDate = this@toEntity.startDate
    endDate = this@toEntity.endDate
    createdAt = this@toEntity.createdAt.atOffset(ZoneOffset.UTC)
    updatedAt = this@toEntity.updatedAt.atOffset(ZoneOffset.UTC)
}

fun SubscriptionEntity.toDomain(): Subscription = Subscription(
    id = id!!,
    userId = userId!!,
    serviceType = serviceType!!,
    plan = plan!!,
    status = status,
    price = price,
    startDate = startDate!!,
    endDate = endDate!!,
    createdAt = createdAt.toInstant(),
    updatedAt = updatedAt.toInstant()
)