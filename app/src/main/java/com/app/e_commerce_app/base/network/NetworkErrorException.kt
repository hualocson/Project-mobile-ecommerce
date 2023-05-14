package com.app.e_commerce_app.base.network

open class NetworkErrorException (val responseMessage: String? = null): Exception() {
}