package com.app.e_commerce_app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.e_commerce_app.model.CartModel

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartModel: CartModel)

    @Update
    suspend fun uppdateCartItem(cartModel: CartModel)

    @Delete
    suspend fun deleteCartItem(cartModel: CartModel)

    @Query("select * from cart_table")
    suspend fun getAllItems(): List<CartModel>

    @Query("select * from cart_table where productId_col = :id")
    suspend fun getItemByProductId(id: Int): List<CartModel>

    @Query("update cart_table set quantity_col = :quantity where productId_col = :id")
    suspend fun updateQuantity(id: Int, quantity: String)

    suspend fun insertOrUpdate(cartModel: CartModel){
        var newQty : Int
        var itemFromDB : List<CartModel> = getItemByProductId(cartModel.productId)
        if(itemFromDB.isEmpty()){
            insertCartItem(cartModel)
        } else{
            newQty = itemFromDB.get(cartModel.id).quantity.toInt() + cartModel.quantity.toInt()
            updateQuantity(cartModel.productId, newQty.toString())
        }
    }
}