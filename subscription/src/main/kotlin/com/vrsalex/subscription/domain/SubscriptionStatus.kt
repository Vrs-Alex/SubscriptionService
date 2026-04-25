package com.vrsalex.subscription.domain

enum class SubscriptionStatus {
    ACTIVE,
    PAUSED,
    CANCELLED,
    EXPIRED;

    fun canTransitionTo(next: SubscriptionStatus): Boolean = when (this) {
        ACTIVE    -> next == PAUSED || next == CANCELLED || next == EXPIRED
        PAUSED    -> next == ACTIVE || next == CANCELLED
        CANCELLED -> false
        EXPIRED   -> next == ACTIVE
    }
}