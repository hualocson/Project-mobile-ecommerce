package com.app.e_commerce_app.model.variation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VaritionData(
    var variations: ArrayList<VariationGetAllModel>
) : Parcelable
