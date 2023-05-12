package com.app.e_commerce_app.database.dao

import androidx.room.*
import com.app.e_commerce_app.model.CartEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Update
    suspend fun uppdateCartItem(cartEntity: CartEntity)

    @Delete
    suspend fun deleteCartItem(cartEntity: CartEntity)

    @Query("select * from cart_table")
    suspend fun getAllItems(): List<CartEntity>

    @Query("select * from cart_table where productId_col = :id")
    suspend fun getItemByProductId(id: Int): List<CartEntity>

    @Query("update cart_table set quantity_col = :quantity where productId_col = :id")
    suspend fun updateQuantity(id: Int, quantity: String)

    @Query("DELETE FROM cart_table")
    fun deleteAll()

    suspend fun insertOrUpdate(cartEntity: CartEntity){
        var newQty : Int
        var itemFromDB : List<CartEntity> = getItemByProductId(cartEntity.productId)
        if(itemFromDB.isEmpty()){
            insertCartItem(cartEntity)
        } else{
            newQty = itemFromDB.get(cartEntity.id).quantity.toInt() + cartEntity.quantity.toInt()
            updateQuantity(cartEntity.productId, newQty.toString())
        }
    }
}