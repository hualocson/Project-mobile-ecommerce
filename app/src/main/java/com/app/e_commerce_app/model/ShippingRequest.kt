package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingRequest(
    val name: String,
    val price: Long,
    val desc: String,
    val icon: String? = "https://cdn-icons-png.flaticon.com/512/3178/3178933.png",
): Parcelable

