package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserJson(
    val email: String,
    private val firstName: String,
    private val lastName: String,
    val phone: String,
    val avatar: String,
) : Parcelable {
    val name
        get(): String {
            return "$firstName $lastName"
        }

    val imageUrl
        get() : String {
            return avatar
        }
}