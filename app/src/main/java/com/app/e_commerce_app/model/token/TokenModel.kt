package com.app.e_commerce_app.model.token

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenModel(
    val accessToken: String,
    val refreshToken: String
): Parcelable