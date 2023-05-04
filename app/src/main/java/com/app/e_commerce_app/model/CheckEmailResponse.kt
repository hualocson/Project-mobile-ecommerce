package com.app.e_commerce_app.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckEmailResponse (
    val message: String
): Parcelable
