package com.marina.surfgallery.auth.domain.entity

sealed class DataResult<T : Any> {
    class Success<T : Any>(val data: T) : DataResult<T>()
    class Error<T : Any>(val code: Int, val message: String?) : DataResult<T>()
    class Exception<T : Any>(val e: Throwable) : DataResult<T>()
}