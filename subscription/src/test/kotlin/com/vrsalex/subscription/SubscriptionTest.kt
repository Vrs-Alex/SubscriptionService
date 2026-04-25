package com.vrsalex.subscription

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.Subscription
import com.vrsalex.subscription.domain.SubscriptionPlan
import com.vrsalex.subscription.domain.SubscriptionStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import kotlin.test.Test

class SubscriptionTest {

    private fun createSubscription(
        status: SubscriptionStatus = SubscriptionStatus.ACTIVE,
        endDate: LocalDate = LocalDate.now().plusMonths(1)
    ) = Subscription(
        id = UUID.randomUUID(),
        userId = UUID.randomUUID(),
        serviceType = ServiceType.SPOTIFY,
        plan = SubscriptionPlan.BASIC,
        status = status,
        price = BigDecimal("9.99"),
        startDate = LocalDate.now(),
        endDate = endDate,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )

    @Test
    fun `active can transition to paused`() {
        val result = createSubscription(SubscriptionStatus.ACTIVE)
            .transitionTo(SubscriptionStatus.PAUSED)
        assertEquals(SubscriptionStatus.PAUSED, result.status)
    }

    @Test
    fun `active can transition to cancelled`() {
        val result = createSubscription(SubscriptionStatus.ACTIVE)
            .transitionTo(SubscriptionStatus.CANCELLED)
        assertEquals(SubscriptionStatus.CANCELLED, result.status)
    }

    @Test
    fun `paused can transition to active`() {
        val result = createSubscription(SubscriptionStatus.PAUSED)
            .transitionTo(SubscriptionStatus.ACTIVE)
        assertEquals(SubscriptionStatus.ACTIVE, result.status)
    }

    @Test
    fun `paused can transition to cancelled`() {
        val result = createSubscription(SubscriptionStatus.PAUSED)
            .transitionTo(SubscriptionStatus.CANCELLED)
        assertEquals(SubscriptionStatus.CANCELLED, result.status)
    }

    @Test
    fun `cancelled cannot transition to any status`() {
        listOf(
            SubscriptionStatus.ACTIVE,
            SubscriptionStatus.PAUSED,
            SubscriptionStatus.EXPIRED
        ).forEach { next ->
            assertThrows<IllegalArgumentException> {
                createSubscription(SubscriptionStatus.CANCELLED).transitionTo(next)
            }
        }
    }

    @Test
    fun `isExpired returns true when end date in past`() {
        assertTrue(
            createSubscription(endDate = LocalDate.now().minusDays(1)).isExpired()
        )
    }

    @Test
    fun `isExpired returns false when end date in future`() {
        assertFalse(
            createSubscription(endDate = LocalDate.now().plusDays(1)).isExpired()
        )
    }

    @Test
    fun `renew sets active status and new end date`() {
        val newEndDate = LocalDate.now().plusMonths(2)
        val result = createSubscription(SubscriptionStatus.EXPIRED).renew(newEndDate)
        assertEquals(SubscriptionStatus.ACTIVE, result.status)
        assertEquals(newEndDate, result.endDate)
    }

    @Test
    fun `renew throws when new end date in past`() {
        assertThrows<IllegalArgumentException> {
            createSubscription(SubscriptionStatus.EXPIRED)
                .renew(LocalDate.now().minusDays(1))
        }
    }

    @Test
    fun `transitionTo updates updatedAt`() {
        val before = Instant.now()
        val result = createSubscription(SubscriptionStatus.ACTIVE)
            .transitionTo(SubscriptionStatus.PAUSED)
        assertTrue(result.updatedAt >= before)
    }
}