package com.app.e_commerce_app.data.api

sealed class NetWorkResult<T: Any> {
    class Success<T : Any> (val data: T): NetWorkResult<T>()
    class Error<T: Any> (val code: Int, val message: String?):NetWorkResult<T>()
    class Exception<T: Any>(val e: Throwable): NetWorkResult<T>()
}
