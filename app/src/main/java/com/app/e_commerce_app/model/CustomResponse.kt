package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

data class CustomResponse<T>(
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    val data: T? = null,

    @SerializedName("statusCode")
    val statusCode: Int
)

