package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressRequest(
    val name: String,
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val addressDetails: String,
    val isDefault: Boolean = false
) : Parcelable