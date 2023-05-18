package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.utils.Utils
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class ProductItemJson(
    val id: Int = 0,
    val productImage: String,
    var price: Long = 0,
    var qtyInStock: Int = 0,
    val productConfigurations: ArrayList<ProductConfigurationJson>
) : Parcelable {
    val getPrice: String
        get() = Utils.formatNumber(price)

    fun toAdminItem(name: String) : ChooseItem {
        val variation = productConfigurations.map {
            "${it.value} "
        }
        var desc = ""
        variation.forEach { item ->
            desc += item
        }
        return ChooseItem(
            id = id,
            title = name,
            desc = desc.trim(),
            icon = productImage,
            price = price
        )
    }
}
