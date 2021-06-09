package com.example.nagwatask.core.utils

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class Progress<out T>(val progress: T): DataState<T>()
}