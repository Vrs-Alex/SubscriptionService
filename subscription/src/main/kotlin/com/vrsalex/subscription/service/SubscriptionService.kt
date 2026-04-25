package com.vrsalex.subscription.service

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionCreate
import com.vrsalex.subscription.domain.SubscriptionStat
import com.vrsalex.subscription.domain.SubscriptionStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.UUID


interface SubscriptionService {

    fun createSubscription(create: SubscriptionCreate): Subscription

    fun getSubscription(id: UUID): Subscription

    fun listSubscriptions(
        userId: UUID?,
        serviceType: ServiceType?,
        status: SubscriptionStatus?,
        from: LocalDate?,
        to: LocalDate?,
        pageable: Pageable
    ): Page<Subscription>

    fun getActiveByUserId(userId: UUID): List<Subscription>

    fun pauseSubscription(id: UUID): Subscription

    fun resumeSubscription(id: UUID): Subscription

    fun cancelSubscription(id: UUID): Subscription

    fun renewSubscription(id: UUID, newEndDate: LocalDate): Subscription

    fun expireSubscription(id: UUID): Subscription

    fun getStats(): SubscriptionStat

}