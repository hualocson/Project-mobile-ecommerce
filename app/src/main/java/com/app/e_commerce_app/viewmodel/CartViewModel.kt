package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CartRespository
import com.app.e_commerce_app.model.CartModel
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : BaseViewModel() {
    private val cartRespository: CartRespository = CartRespository(application)
    private val _cartsData = MutableLiveData<List<CartModel>>()
    val cartsData: LiveData<List<CartModel>> = _cartsData

    fun insertCart(cartModel: CartModel) = viewModelScope.launch {
        cartRespository.insertCart(cartModel)
        getAllItems()
    }

    fun updateCart(cartModel: CartModel) = viewModelScope.launch {
        cartRespository.updateCart(cartModel)
    }
//    fun deletetCart(cartModel:CartModel) = viewModelScope.launch {
//        cartRespository.deleteCart(cartModel)
//    }

    fun insertOrUpdate(cartModel: CartModel) = viewModelScope.launch {
        cartRespository.insertOrUpdate(cartModel)
        getAllItems()
    }
    fun deleteCart(cartModel: CartModel) {
        parentJob = viewModelScope.launch(handler) {
            cartRespository.deleteCart(cartModel)
        }
        registerJobFinish()
        getAllItems()
    }

    fun getAllItems() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = cartRespository.getAllItems()
            _cartsData.postValue(response)
        }
        registerJobFinish()
    }

    class CartViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}