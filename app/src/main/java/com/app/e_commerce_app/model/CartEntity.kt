package com.app.e_commerce_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("cart_table")
data class CartEntity(
    @ColumnInfo("productId_col")
    var productId: Int,
    @ColumnInfo("name_col")
    var name: String = "",
    @ColumnInfo("img_col")
    var img: String = "",
    @ColumnInfo("price_col")
    var price: Long,
    @ColumnInfo("quantity_col")
    var quantity: Int,
    @ColumnInfo("desc_col")
    var desc: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id_col")
    var id: Int = 0
}