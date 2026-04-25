package com.vrsalex.subscription.database.repository.impl.jpa

import com.vrsalex.subscription.database.entity.SubscriptionEntity
import com.vrsalex.subscription.domain.SubscriptionStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.UUID


interface SubscriptionJpaRepository : JpaRepository<SubscriptionEntity, UUID> {

    fun findAllByUserIdAndStatus(
        userId: UUID,
        status: SubscriptionStatus
    ): List<SubscriptionEntity>

    fun findAllByStatus(status: SubscriptionStatus): List<SubscriptionEntity>

    @Query(
        value = """
            SELECT * FROM subscription
            WHERE (CAST(:userId AS UUID) IS NULL OR user_id = CAST(:userId AS UUID))
              AND (CAST(:serviceType AS VARCHAR) IS NULL OR service_type = :serviceType)
              AND (CAST(:status AS VARCHAR) IS NULL OR status = :status)
              AND (CAST(:from AS DATE) IS NULL OR start_date >= CAST(:from AS DATE))
              AND (CAST(:to AS DATE) IS NULL OR end_date <= CAST(:to AS DATE))
            ORDER BY created_at DESC
        """,
        countQuery = """
            SELECT COUNT(*) FROM subscription
            WHERE (CAST(:userId AS UUID) IS NULL OR user_id = CAST(:userId AS UUID))
              AND (CAST(:serviceType AS VARCHAR) IS NULL OR service_type = :serviceType)
              AND (CAST(:status AS VARCHAR) IS NULL OR status = :status)
              AND (CAST(:from AS DATE) IS NULL OR start_date >= CAST(:from AS DATE))
              AND (CAST(:to AS DATE) IS NULL OR end_date <= CAST(:to AS DATE))
        """,
        nativeQuery = true
    )
    fun findWithFilters(
        @Param("userId") userId: String?,
        @Param("serviceType") serviceType: String?,
        @Param("status") status: String?,
        @Param("from") from: LocalDate?,
        @Param("to") to: LocalDate?,
        pageable: Pageable
    ): Page<SubscriptionEntity>


    @Query("SELECT COUNT(s) FROM SubscriptionEntity s WHERE s.status = :status")
    fun countByStatus(@Param("status") status: SubscriptionStatus): Long

}