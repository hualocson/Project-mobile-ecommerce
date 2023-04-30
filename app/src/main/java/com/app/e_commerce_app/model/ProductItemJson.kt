package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItemJson(
    val id: Int,
    val productImage: String,
    val price: Int,
    val qtyInStock: Int,
    val productConfigurations: ArrayList<ProductConfigurationJson>
) : Parcelable
