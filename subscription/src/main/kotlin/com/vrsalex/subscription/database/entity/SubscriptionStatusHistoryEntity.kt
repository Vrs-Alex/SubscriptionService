package com.vrsalex.subscription.database.entity

import com.vrsalex.subscription.domain.SubscriptionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "subscription_status_history")
open class SubscriptionStatusHistoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subscription_id", nullable = false)
    open var subscription: SubscriptionEntity? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 50)
    open var oldStatus: SubscriptionStatus? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 50)
    open var newStatus: SubscriptionStatus = SubscriptionStatus.ACTIVE

    @Column(name = "timestamp", nullable = false)
    open var timestamp: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "message")
    open var message: String? = null
}