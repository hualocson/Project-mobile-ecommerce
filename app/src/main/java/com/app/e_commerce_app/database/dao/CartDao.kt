package com.app.e_commerce_app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.e_commerce_app.model.CartModel

@Dao
interface CartDao {

    @Insert
    suspend fun insertCartItem(cartModel: CartModel)

    @Update
    suspend fun uppdateCartItem(cartModel: CartModel)

    @Delete
    suspend fun deleteCartItem(cartModel: CartModel)

    @Query("select * from cart_table")
    suspend fun getAllItems(): List<CartModel>
}