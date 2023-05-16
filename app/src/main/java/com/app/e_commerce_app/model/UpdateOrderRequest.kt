package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.utils.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateOrderRequest(
    val status: String
): Parcelable
