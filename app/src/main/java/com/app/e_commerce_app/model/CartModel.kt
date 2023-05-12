package com.app.e_commerce_app.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.e_commerce_app.model.order.OrderLineJson
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity("cart_table")
data class CartModel(
    @ColumnInfo("productId_col")
    var productId: Int,
    @ColumnInfo("name_col")
    var name: String = "",
    @ColumnInfo("img_col")
    var img: String = "",
    @ColumnInfo("price_col")
    var price: String,
    @ColumnInfo("quantity_col")
    var quantity: String,
    @ColumnInfo("desc_col")
    var desc: String
):Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id_col")
    var id: Int = 0

    fun toOrderLineJson() : OrderLineJson {
        return OrderLineJson(productId, quantity.toInt(), price.toLong())
    }
}