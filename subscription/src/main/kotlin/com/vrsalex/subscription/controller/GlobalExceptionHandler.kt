package com.vrsalex.subscription.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NoSuchElementException) =
        mapOf("error" to e.message)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: IllegalArgumentException) =
        mapOf("error" to e.message)

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleConflict(e: IllegalStateException) =
        mapOf("error" to e.message)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUnexpected(e: Exception) =
        mapOf("error" to "Внутренняя ошибка сервера")
}