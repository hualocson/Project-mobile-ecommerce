package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingJson(
    val id: Int,
    val name: String,
    val price: Long,
    val iconUrl : String,
    val description: String
): Parcelable {
    fun toChooseItem() :ChooseItem {
        return ChooseItem(id, title = name, desc = description, icon = iconUrl, price = price)
    }

    val priceString
    get() = price.toString()
}
