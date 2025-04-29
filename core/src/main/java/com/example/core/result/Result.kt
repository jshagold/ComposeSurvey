package com.example.core.result

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val message: String, val cause: Throwable? = null) : Result<Nothing>()
}