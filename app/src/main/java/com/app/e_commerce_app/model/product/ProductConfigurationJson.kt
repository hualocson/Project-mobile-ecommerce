package com.app.e_commerce_app.model.product

import android.os.Parcelable
import com.app.e_commerce_app.model.variation.VariationModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductConfigurationJson(

    val id: Int,
    val variation: String,

    val value: String
) : Parcelable
