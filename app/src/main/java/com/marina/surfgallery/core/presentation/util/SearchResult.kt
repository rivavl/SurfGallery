package com.marina.surfgallery.core.presentation.util

sealed class SearchResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : SearchResult<T>(data)
    class Error<T>(message: String, data: T? = null) : SearchResult<T>(data, message)
    class Loading<T>(data: T? = null) : SearchResult<T>(data)
    class NoResults<T>() : SearchResult<T>()
    class IsEmpty<T> : SearchResult<T>()
}