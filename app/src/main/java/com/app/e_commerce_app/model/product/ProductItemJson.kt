package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.utils.Utils
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class ProductItemJson(
    val id: Int = 0,
    val productImage: String,
    val price: Long = 0,
    val qtyInStock: Int = 0,
    val productConfigurations: ArrayList<ProductConfigurationJson>
) : Parcelable {
    val getPrice: String
        get() = Utils.formatNumber(price)
}
