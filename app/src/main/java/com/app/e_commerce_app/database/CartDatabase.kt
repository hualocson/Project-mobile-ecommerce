package com.app.e_commerce_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.e_commerce_app.database.dao.CartDao
import com.app.e_commerce_app.model.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class CartDatabase :RoomDatabase(){
    abstract fun getCartDao(): CartDao

    companion object {
        @Volatile
        private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, CartDatabase::class.java, "CartDatabase").build()
            }
            return instance!!
        }
    }
}