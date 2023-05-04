package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    val email: String,
    val password: String,
    val avatar: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
): Parcelable
