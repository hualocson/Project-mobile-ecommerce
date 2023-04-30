package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

data class CustomResponse<T>(
    var success: Boolean,

    var message: String,

    val data: T? = null,

    val statusCode: Int
)

