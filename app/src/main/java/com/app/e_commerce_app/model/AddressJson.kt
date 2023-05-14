package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressJson(
    val id: Int,
    val name: String,
    val userId: Int,
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val addressDetails: String,
    val isDefault: Boolean,
) : Parcelable {
    val def
        get() = isDefault
    val fullAddress
        get() = "$addressDetails, $street, $city, $state"

    fun toChooseItem(): ChooseItem {
        return ChooseItem(id, title = name, desc = fullAddress, iconId = R.drawable.ic_outline_location_24)
    }
}
