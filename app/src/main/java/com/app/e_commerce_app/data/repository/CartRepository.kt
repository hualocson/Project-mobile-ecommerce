package com.app.e_commerce_app.data.repository

import android.app.Application
import com.app.e_commerce_app.database.CartDatabase
import com.app.e_commerce_app.database.dao.CartDao
import com.app.e_commerce_app.model.CartEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDao: CartDao) {
//    private val cartDao: CartDao
//
//    init {
//        val cartDatabase: CartDatabase = CartDatabase.getInstance(app)
//        cartDao = cartDatabase.getCartDao()
//    }

    suspend fun insertCart(cartEntity: CartEntity) = cartDao.insertCartItem(cartEntity)
    suspend fun updateCart(cartEntity: CartEntity) = cartDao.uppdateCartItem(cartEntity)
    suspend fun deleteCart(cartEntity: CartEntity) = cartDao.deleteCartItem(cartEntity)

    suspend fun getAllItems(): List<CartEntity> = withContext(Dispatchers.IO) {
        cartDao.getAllItems()
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        cartDao.deleteAll()
    }

    suspend fun insertOrUpdate(cartEntity: CartEntity) = cartDao.insertOrUpdate(cartEntity)
}