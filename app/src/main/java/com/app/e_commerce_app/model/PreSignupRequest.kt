package com.app.e_commerce_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreSignupRequest(
    val email : String
): Parcelable
