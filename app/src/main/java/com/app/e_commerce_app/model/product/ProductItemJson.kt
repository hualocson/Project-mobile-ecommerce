package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.model.product.ProductConfigurationJson
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItemJson(
    val id: Int,
    val productImage: String,
    val price: Int,
    val qtyInStock: Int,
    val productConfigurations: ArrayList<ProductConfigurationJson>
) : Parcelable {
}
