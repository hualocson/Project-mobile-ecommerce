package com.app.e_commerce_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("cart_table")
data class CartModel(
    @ColumnInfo("name_col")
    var name: String = "",
    @ColumnInfo("img_col")
    var img: String = "",
    @ColumnInfo("price_col")
    var price: String,
    @ColumnInfo("quantity_col")
    var quantity: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id_col")
    var id: Int = 0
}