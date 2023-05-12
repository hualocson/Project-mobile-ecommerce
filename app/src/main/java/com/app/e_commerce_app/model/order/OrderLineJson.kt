package com.app.e_commerce_app.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderLineJson(
    val id: Int,
    val productItemId: Int,
    val orderId: Int,
    val qty: Int,
    val price: Long
) : Parcelable {
    constructor(productItemId: Int, qty: Int, price: Long) : this(
        id = 0,
        productItemId = productItemId,
        orderId = 0,
        qty = qty,
        price = price
    )
}
