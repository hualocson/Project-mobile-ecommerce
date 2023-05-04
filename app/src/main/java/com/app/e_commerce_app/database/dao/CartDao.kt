package com.app.e_commerce_app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.e_commerce_app.model.CartModel

@Dao
interface CartDao {

    @Insert
    suspend fun insertCartItem(cartModel: CartModel)

    @Update
    suspend fun uppdateCartItem(cartModel: CartModel)

    @Update
    suspend fun deleteCartItem(cartModel: CartModel)

    @Query("select * from cart_table")
    suspend fun getAllItems(): List<CartModel>
}