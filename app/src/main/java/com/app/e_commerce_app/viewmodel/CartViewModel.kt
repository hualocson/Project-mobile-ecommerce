package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.CartRespository
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.model.product.ProductDetailModel
import com.app.e_commerce_app.model.product.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : BaseViewModel() {
    private val cartRespository: CartRespository = CartRespository(application)
    private val _cartsData = MutableLiveData<List<CartModel>>()
    val cartsData: LiveData<List<CartModel>> = _cartsData

    fun insertCart(cartModel:CartModel) = viewModelScope.launch {
        cartRespository.insertCart(cartModel)
    }
    fun updateCart(cartModel:CartModel) = viewModelScope.launch {
        cartRespository.updateCart(cartModel)
    }
    fun deletetCart(cartModel:CartModel) = viewModelScope.launch {
        cartRespository.deleteCart(cartModel)
    }

    fun getAllItems() {
        showLoading(true)
        parentJob = viewModelScope.launch(Dispatchers.IO) {
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