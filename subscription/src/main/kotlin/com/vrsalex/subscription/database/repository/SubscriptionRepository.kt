package com.vrsalex.subscription.database.repository

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.UUID


interface SubscriptionRepository {

    fun save(subscription: Subscription): Subscription

    fun findById(id: UUID): Subscription?

    fun findWithFilters(
        userId: UUID?,
        serviceType: ServiceType?,
        status: SubscriptionStatus?,
        from: LocalDate?,
        to: LocalDate?,
        pageable: Pageable
    ): Page<Subscription>

    fun findActiveByUserId(userId: UUID): List<Subscription>

    fun findAllByStatus(status: SubscriptionStatus): List<Subscription>

    fun countByStatus(status: SubscriptionStatus): Long

    fun countAll(): Long

}