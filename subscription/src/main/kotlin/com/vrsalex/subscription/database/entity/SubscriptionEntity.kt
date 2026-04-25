package com.vrsalex.subscription.database.entity

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.SubscriptionPlan
import com.vrsalex.subscription.domain.SubscriptionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID


@Entity
@Table(name = "subscription")
open class SubscriptionEntity {
    @Id
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @Column(name = "user_id", nullable = false)
    open var userId: UUID? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 50)
    open var serviceType: ServiceType? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "plan", nullable = false, length = 50)
    open var plan: SubscriptionPlan? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    open var status: SubscriptionStatus = SubscriptionStatus.ACTIVE

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    open var price: BigDecimal = BigDecimal.ZERO

    @Column(name = "start_date", nullable = false)
    open var startDate: LocalDate? = null

    @Column(name = "end_date", nullable = false)
    open var endDate: LocalDate? = null

    @Column(name = "created_at", nullable = false)
    open var createdAt: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "updated_at", nullable = false)
    open var updatedAt: OffsetDateTime = OffsetDateTime.now()
}