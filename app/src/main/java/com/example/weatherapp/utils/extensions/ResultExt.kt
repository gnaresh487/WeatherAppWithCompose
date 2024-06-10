package com.example.weatherapp.utils.extensions

import java.util.concurrent.CancellationException

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its encapsulated result if invocation was successful,
 * catching any [Throwable] exception (except [Error] or [CancellationException]) that was thrown from the [block] function execution and encapsulating it as a failure.
 */
inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        return when (e) {
            is Error, is CancellationException -> throw e
            else -> Result.failure(e)
        }
    }
}