package com.app.e_commerce_app.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderRequest(
    val orderTotal: Long,
    val shippingMethodId: Int,
    val shippingAddressId: Int,
    val orderLines : List<OrderLineJson>
) : Parcelable {
}