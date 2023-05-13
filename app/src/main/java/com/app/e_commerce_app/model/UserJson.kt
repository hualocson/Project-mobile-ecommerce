package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.utils.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserJson(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val avatar: String,
//    val gender: String,
val gender: Gender
) : Parcelable {
    val name
        get(): String {
            return "$firstName $lastName"
        }

    val imageUrl
        get() : String {
            return avatar
        }


    fun isValidateFirstName(): Boolean{
        return firstName.isNotEmpty()
    }

    fun isValidateLastName():Boolean{
        return lastName.isNotEmpty()
    }
}