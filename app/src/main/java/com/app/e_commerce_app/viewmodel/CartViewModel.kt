package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CartRespository
import com.app.e_commerce_app.model.CartModel
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : BaseViewModel() {
    private val cartRepository: CartRespository = CartRespository(application)
    private val _cartsData = MutableLiveData<List<CartModel>>()
    val cartsData: LiveData<List<CartModel>> = _cartsData

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> = _totalPrice

    fun insertCart(cartModel: CartModel) = viewModelScope.launch {
        cartRepository.insertCart(cartModel)
        getAllItems()
    }

    fun updateCart(cartModel: CartModel) = viewModelScope.launch(handler) {
        cartRepository.updateCart(cartModel)
        updateTotalPrice(cartsData.value!!)
    }
//    fun deletetCart(cartModel:CartModel) = viewModelScope.launch {
//        cartRespository.deleteCart(cartModel)
//    }

    fun insertOrUpdate(cartModel: CartModel) = viewModelScope.launch {
        cartRepository.insertOrUpdate(cartModel)
        getAllItems()
    }

    fun deleteCart(cartModel: CartModel) {
        parentJob = viewModelScope.launch(handler) {
            cartRepository.deleteCart(cartModel)
            val response = cartRepository.getAllItems()
            updateTotalPrice(response)
            _cartsData.postValue(response)
        }
        registerJobFinish()
        getAllItems()
    }

    private fun updateTotalPrice(items: List<CartModel>) {
        var total = 0
        items.forEach { item ->
            total += (item.price.toInt() * item.quantity.toInt())
        }
        _totalPrice.postValue(total.toString())
    }

    fun getAllItems() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val response = cartRepository.getAllItems()
            updateTotalPrice(response)
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