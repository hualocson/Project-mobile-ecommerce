package com.app.e_commerce_app.base.network

public open class NetworkErrorException (val responseMessage: String? = null): Exception() {
}