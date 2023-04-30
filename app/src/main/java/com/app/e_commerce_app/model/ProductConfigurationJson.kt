package com.app.e_commerce_app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductConfigurationJson(

    val variation: String,

    val value: String
) : Parcelable
