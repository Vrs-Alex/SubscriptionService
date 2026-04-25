package com.vrsalex.subscription.dto.mapper

import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionCreate
import com.vrsalex.subscription.domain.SubscriptionStat
import com.vrsalex.subscription.domain.SubscriptionStatusHistory
import com.vrsalex.subscription.dto.model.CreateSubscriptionRequest
import com.vrsalex.subscription.dto.model.PageResponse
import com.vrsalex.subscription.dto.model.SubscriptionResponse
import com.vrsalex.subscription.dto.model.SubscriptionStatResponse
import com.vrsalex.subscription.dto.model.SubscriptionStatusHistoryResponse
import org.springframework.data.domain.Page


fun CreateSubscriptionRequest.toDomain() = SubscriptionCreate(
    userId = userId,
    serviceType = serviceType,
    plan = plan,
    price = price,
    startDate = startDate,
    endDate = endDate
)

fun Subscription.toResponse() = SubscriptionResponse(
    id = id,
    userId = userId,
    serviceType = serviceType,
    plan = plan,
    status = status,
    price = price,
    startDate = startDate,
    endDate = endDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun SubscriptionStatusHistory.toResponse() = SubscriptionStatusHistoryResponse(
    id = id,
    subscriptionId = subscriptionId,
    oldStatus = oldStatus,
    newStatus = newStatus,
    timestamp = timestamp,
    message = message
)

fun SubscriptionStat.toResponse() = SubscriptionStatResponse(
    total = total,
    active = active,
    paused = paused,
    cancelled = cancelled,
    expired = expired
)

fun <T: Any> Page<T>.toPageResponse() = PageResponse(
    content = content,
    page = number,
    size = size,
    totalElements = totalElements,
    totalPages = totalPages
)