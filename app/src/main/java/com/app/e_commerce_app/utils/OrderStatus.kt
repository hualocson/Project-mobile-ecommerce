package com.app.e_commerce_app.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OrderStatus(val value: String) : Parcelable {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED")
}