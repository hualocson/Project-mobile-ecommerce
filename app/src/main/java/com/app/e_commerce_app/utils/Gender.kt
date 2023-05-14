package com.app.e_commerce_app.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Gender(val value: String):Parcelable {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER"),
    UNKNOWN("UNKNOWN")
}