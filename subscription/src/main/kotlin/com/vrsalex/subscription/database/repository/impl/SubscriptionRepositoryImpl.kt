package com.vrsalex.subscription.database.repository.impl

import com.vrsalex.subscription.database.mapper.toDomain
import com.vrsalex.subscription.database.mapper.toEntity
import com.vrsalex.subscription.database.repository.SubscriptionRepository
import com.vrsalex.subscription.database.repository.impl.jpa.SubscriptionJpaRepository
import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID


@Repository
class SubscriptionRepositoryImpl(
    private val jpa: SubscriptionJpaRepository
) : SubscriptionRepository {

    override fun save(subscription: Subscription): Subscription =
        jpa.save(subscription.toEntity()).toDomain()

    override fun findById(id: UUID): Subscription? =
        jpa.findById(id).orElse(null)?.toDomain()

    override fun findWithFilters(
        userId: UUID?,
        serviceType: ServiceType?,
        status: SubscriptionStatus?,
        from: LocalDate?,
        to: LocalDate?,
        pageable: Pageable
    ): Page<Subscription> {
        val pageRequest = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize
        )
        return jpa.findWithFilters(
            userId = userId?.toString(),
            serviceType = serviceType?.name,
            status = status?.name,
            from = from,
            to = to,
            pageable = pageRequest
        ).map { it.toDomain() }
    }

    override fun findActiveByUserId(userId: UUID): List<Subscription> =
        jpa.findAllByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
            .map { it.toDomain() }

    override fun findAllByStatus(status: SubscriptionStatus): List<Subscription> =
        jpa.findAllByStatus(status).map { it.toDomain() }


    override fun countByStatus(status: SubscriptionStatus): Long =
        jpa.countByStatus(status)

    override fun countAll(): Long =
        jpa.count()

}