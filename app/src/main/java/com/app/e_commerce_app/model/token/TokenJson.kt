package com.app.e_commerce_app.model.token

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenJson(
    val accessToken: String,
    val expiresAccess: String,
    val refreshToken: String,
    val expiresRefresh: String
) : Parcelable {
    fun toTokenModel(): TokenModel {
        return TokenModel(accessToken, refreshToken)
    }
}