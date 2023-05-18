package com.app.e_commerce_app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val id: Int,

    var categoryName: String,

    @SerializedName("icUrl")
    var categoryIc: String,
) : Parcelable {
    fun toCategoryRadio(): CategoryRadioButton {
        return CategoryRadioButton(id = id, categoryName = categoryName, isChecked = false)
    }

    fun toAdminChooseItem(): ChooseItem {
        return ChooseItem(
            id = id,
            title = categoryName,
            icon = categoryIc,
            desc = ""
        )
    }
}
