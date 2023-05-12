package com.app.e_commerce_app.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderLineJson(
    val id : Int,
    val productItemId: Int,
    val orderId: Int,
    val qty: Int,
    val price: Long
): Parcelable
