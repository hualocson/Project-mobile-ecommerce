package com.app.e_commerce_app.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderUpdateJson(
    val id: Int,
    val userId: Int,
    val orderTotal: Long,
    val shippingMethodId: Int,
    val shippingAddressId: Int,
    val orderStatus: String,
    val createdAt: String,
    val updatedAt: String
): Parcelable