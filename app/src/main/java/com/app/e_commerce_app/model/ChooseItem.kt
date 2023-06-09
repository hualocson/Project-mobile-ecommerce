package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.utils.Utils
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.util.*

@Parcelize
data class ChooseItem(
    val id: Int,
    var isChecked: Boolean = false,
    val title: String,
    val desc: String,
    val icon: String? = null,
    val iconId: Int? = null,
    val price: Long? = null
) : Parcelable {
    fun toAddressJson() :AddressJson {
        val data = desc.split(", ")
        return AddressJson(id = id, name = title, 0, addressDetails = data[0], street = data[1], city = data[2], state = data[3], isDefault = false, zip = "0")
    }

    fun toShippingJson() :ShippingJson {
        return ShippingJson(id, title, price!!, icon!!, desc)
    }
}