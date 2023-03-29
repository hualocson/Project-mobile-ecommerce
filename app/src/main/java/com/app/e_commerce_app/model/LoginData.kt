package com.app.e_commerce_app.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("accountId")
    val accountId: Int
)
