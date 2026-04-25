package com.vrsalex.subscription.scheduler

import com.vrsalex.subscription.database.repository.SubscriptionRepository
import com.vrsalex.subscription.domain.SubscriptionStatus
import com.vrsalex.subscription.service.SubscriptionService
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
@EnableScheduling
class SubscriptionScheduler(
    private val subscriptionRepository: SubscriptionRepository,
    private val subscriptionService: SubscriptionService
) {
    private val log = LoggerFactory.getLogger(SubscriptionScheduler::class.java)


    @Scheduled(cron = "0 0 0 * * *")
    @PostConstruct
    fun expireSubscriptions() {
        log.info("Запуск проверки истекших подписок")

        val expired = subscriptionRepository.findAllByStatus(SubscriptionStatus.ACTIVE)
            .filter { it.isExpired() }

        expired.forEach { subscription ->
            runCatching {
                subscriptionService.expireSubscription(subscription.id)
                log.info("Подписка ${subscription.id} переведена в EXPIRED")
            }.onFailure { e ->
                log.error("Ошибка при переводе подписки ${subscription.id} в EXPIRED", e)
            }
        }

        log.info("Проверка завершена, обработано ${expired.size} подписок")
    }
}