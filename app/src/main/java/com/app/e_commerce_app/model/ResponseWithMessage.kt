package com.app.e_commerce_app.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseWithMessage (
    val message: String
): Parcelable
