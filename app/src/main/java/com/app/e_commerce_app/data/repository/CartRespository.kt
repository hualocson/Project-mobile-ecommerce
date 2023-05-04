package com.app.e_commerce_app.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.e_commerce_app.database.CartDatabase
import com.app.e_commerce_app.database.dao.CartDao
import com.app.e_commerce_app.model.CartModel

class CartRespository(app:Application) {
    private val cartDao : CartDao
    init {
        val cartDatabase:CartDatabase = CartDatabase.getInstance(app)
        cartDao = cartDatabase.getCartDao()
    }

    suspend fun insertCart(cartModel:CartModel) = cartDao.insertCartItem(cartModel)
    suspend fun updateCart(cartModel: CartModel) = cartDao.uppdateCartItem(cartModel)
    suspend fun deleteCart(cartModel: CartModel) = cartDao.deleteCartItem(cartModel)

    suspend fun  getAllItems(): List<CartModel> = cartDao.getAllItems()
}