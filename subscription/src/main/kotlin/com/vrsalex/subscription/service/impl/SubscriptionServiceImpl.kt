package com.vrsalex.subscription.service.impl

import com.vrsalex.subscription.database.repository.SubscriptionRepository
import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionCreate
import com.vrsalex.subscription.domain.SubscriptionStat
import com.vrsalex.subscription.domain.SubscriptionStatus
import com.vrsalex.subscription.service.SubscriptionService
import com.vrsalex.subscription.service.SubscriptionStatusHistoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID


@Service
class SubscriptionServiceImpl(
    private val subscriptionRepository: SubscriptionRepository,
    private val historyService: SubscriptionStatusHistoryService
) : SubscriptionService {

    override fun createSubscription(create: SubscriptionCreate): Subscription {
        require(create.endDate.isAfter(create.startDate)) {
            "Дата окончания должна быть позже даты начала"
        }
        require(create.price >= BigDecimal.ZERO) {
            "Стоимость не может быть отрицательной"
        }

        val subscription = Subscription(
            id = UUID.randomUUID(),
            userId = create.userId,
            serviceType = create.serviceType,
            plan = create.plan,
            status = SubscriptionStatus.ACTIVE,
            price = create.price,
            startDate = create.startDate,
            endDate = create.endDate,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val saved = subscriptionRepository.save(subscription)
        historyService.save(saved, null, "Подписка создана")
        return saved
    }

    override fun getSubscription(id: UUID): Subscription =
        subscriptionRepository.findById(id)
            ?: throw NoSuchElementException("Подписка не найдена: $id")

    override fun listSubscriptions(
        userId: UUID?,
        serviceType: ServiceType?,
        status: SubscriptionStatus?,
        from: LocalDate?,
        to: LocalDate?,
        pageable: Pageable
    ): Page<Subscription> =
        subscriptionRepository.findWithFilters(
            userId, serviceType, status, from, to, pageable
        )

    override fun getActiveByUserId(userId: UUID): List<Subscription> =
        subscriptionRepository.findActiveByUserId(userId)

    override fun pauseSubscription(id: UUID): Subscription {
        val subscription = getSubscription(id)
        val oldStatus = subscription.status
        val updated = subscription.transitionTo(SubscriptionStatus.PAUSED)
        val saved = subscriptionRepository.save(updated)
        historyService.save(saved, oldStatus, "Подписка приостановлена")
        return saved
    }

    override fun resumeSubscription(id: UUID): Subscription {
        val subscription = getSubscription(id)
        val oldStatus = subscription.status
        val updated = subscription.transitionTo(SubscriptionStatus.ACTIVE)
        val saved = subscriptionRepository.save(updated)
        historyService.save(saved, oldStatus, "Подписка возобновлена")
        return saved
    }

    override fun cancelSubscription(id: UUID): Subscription {
        val subscription = getSubscription(id)
        val oldStatus = subscription.status
        val updated = subscription.transitionTo(SubscriptionStatus.CANCELLED)
        val saved = subscriptionRepository.save(updated)
        historyService.save(saved, oldStatus, "Подписка отменена")
        return saved
    }

    override fun renewSubscription(id: UUID, newEndDate: LocalDate): Subscription {
        val subscription = getSubscription(id)
        val oldStatus = subscription.status
        val updated = subscription.renew(newEndDate)
        val saved = subscriptionRepository.save(updated)
        historyService.save(saved, oldStatus, "Подписка продлена до $newEndDate")
        return saved
    }

    override fun expireSubscription(id: UUID): Subscription {
        val subscription = getSubscription(id)
        val oldStatus = subscription.status
        val updated = subscription.transitionTo(SubscriptionStatus.EXPIRED)
        val saved = subscriptionRepository.save(updated)
        historyService.save(saved, oldStatus, "Подписка истекла")
        return saved
    }

    override fun getStats(): SubscriptionStat = SubscriptionStat(
        total = subscriptionRepository.countAll(),
        active = subscriptionRepository.countByStatus(SubscriptionStatus.ACTIVE),
        paused = subscriptionRepository.countByStatus(SubscriptionStatus.PAUSED),
        cancelled = subscriptionRepository.countByStatus(SubscriptionStatus.CANCELLED),
        expired = subscriptionRepository.countByStatus(SubscriptionStatus.EXPIRED)
    )

}