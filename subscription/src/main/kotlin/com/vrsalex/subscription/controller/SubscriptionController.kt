package com.vrsalex.subscription.controller

import com.vrsalex.subscription.domain.ServiceType
import com.vrsalex.subscription.domain.SubscriptionStatus
import com.vrsalex.subscription.dto.mapper.toDomain
import com.vrsalex.subscription.dto.mapper.toPageResponse
import com.vrsalex.subscription.dto.mapper.toResponse
import com.vrsalex.subscription.dto.model.CreateSubscriptionRequest
import com.vrsalex.subscription.dto.model.PageResponse
import com.vrsalex.subscription.dto.model.RenewSubscriptionRequest
import com.vrsalex.subscription.dto.model.SubscriptionResponse
import com.vrsalex.subscription.dto.model.SubscriptionStatResponse
import com.vrsalex.subscription.dto.model.SubscriptionStatusHistoryResponse
import com.vrsalex.subscription.service.SubscriptionService
import com.vrsalex.subscription.service.SubscriptionStatusHistoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID


@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscriptions", description = "Управление подписками")
class SubscriptionController(
    private val subscriptionService: SubscriptionService,
    private val historyService: SubscriptionStatusHistoryService
) {

    @Operation(summary = "Создать подписку")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSubscription(
        @RequestBody request: CreateSubscriptionRequest
    ): SubscriptionResponse =
        subscriptionService.createSubscription(request.toDomain()).toResponse()

    @Operation(summary = "Получить подписку по ID")
    @GetMapping("/{id}")
    fun getSubscription(@PathVariable id: UUID): SubscriptionResponse =
        subscriptionService.getSubscription(id).toResponse()

    @Operation(summary = "Список подписок с фильтрами и пагинацией")
    @GetMapping
    fun listSubscriptions(
        @RequestParam userId: UUID? = null,
        @RequestParam serviceType: ServiceType? = null,
        @RequestParam status: SubscriptionStatus? = null,
        @RequestParam from: LocalDate? = null,
        @RequestParam to: LocalDate? = null,
        @PageableDefault(size = 20) pageable: Pageable
    ): PageResponse<SubscriptionResponse> =
        subscriptionService.listSubscriptions(
            userId, serviceType, status, from, to, pageable
        ).map { it.toResponse() }.toPageResponse()

    @Operation(summary = "Активные подписки пользователя")
    @GetMapping("/user/{userId}/active")
    fun getActiveByUserId(@PathVariable userId: UUID): List<SubscriptionResponse> =
        subscriptionService.getActiveByUserId(userId).map { it.toResponse() }

    @Operation(summary = "Приостановить подписку")
    @PatchMapping("/{id}/pause")
    fun pauseSubscription(@PathVariable id: UUID): SubscriptionResponse =
        subscriptionService.pauseSubscription(id).toResponse()

    @Operation(summary = "Возобновить подписку")
    @PatchMapping("/{id}/resume")
    fun resumeSubscription(@PathVariable id: UUID): SubscriptionResponse =
        subscriptionService.resumeSubscription(id).toResponse()

    @Operation(summary = "Отменить подписку")
    @PatchMapping("/{id}/cancel")
    fun cancelSubscription(@PathVariable id: UUID): SubscriptionResponse =
        subscriptionService.cancelSubscription(id).toResponse()

    @Operation(summary = "Продлить подписку")
    @PatchMapping("/{id}/renew")
    fun renewSubscription(
        @PathVariable id: UUID,
        @RequestBody request: RenewSubscriptionRequest
    ): SubscriptionResponse =
        subscriptionService.renewSubscription(id, request.newEndDate).toResponse()

    @Operation(summary = "История изменения статусов")
    @GetMapping("/{id}/history")
    fun getHistory(@PathVariable id: UUID): List<SubscriptionStatusHistoryResponse> =
        historyService.findAllBySubscriptionId(id).map { it.toResponse() }

    @Operation(summary = "Статистика по подпискам")
    @GetMapping("/stats")
    fun getStats(): SubscriptionStatResponse =
        subscriptionService.getStats().toResponse()
}