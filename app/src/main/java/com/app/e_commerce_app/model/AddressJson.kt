package com.app.e_commerce_app.model

import android.os.Parcelable
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
): Parcelable
