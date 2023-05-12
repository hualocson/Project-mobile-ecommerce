package com.app.e_commerce_app.model

import android.os.Parcelable
import com.app.e_commerce_app.model.order.OrderLineJson
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItemModel(
    val id: Int,
    var productId: Int,
    var name: String = "",
    var img: String = "",
    var price: Long,
    var quantity: Int,
    var desc: String
) : Parcelable {
    fun toCartEntity(): CartEntity {
        return CartEntity(
            productId = productId,
            name = name,
            img = img,
            price = price,
            quantity = quantity,
            desc = desc
        )
    }

    fun toOrderLineJson(): OrderLineJson {
        return OrderLineJson(
            productItemId = productId,
            qty = quantity,
            price = price
        )
    }
}

fun CartEntity.toCartItemModel(): CartItemModel {
    return CartItemModel(
        id = id,
        productId = productId,
        name = name,
        img = img,
        price = price,
        quantity = quantity,
        desc = desc
    )
}