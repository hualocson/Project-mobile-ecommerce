package com.app.e_commerce_app.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItemJson(
    val id: Int = 0,
    val productImage: String,
    val price: Int = 0,
    val qtyInStock: Int = 0,
    val productConfigurations: ArrayList<ProductConfigurationJson>
) : Parcelable {
    val getPrice
        get() = price.toString()
}
