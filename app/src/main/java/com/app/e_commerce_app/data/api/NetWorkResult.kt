package com.app.e_commerce_app.data.api

sealed class NetWorkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetWorkResult<T>()
    data class Error(val exception: Exception) : NetWorkResult<Nothing>()
}
