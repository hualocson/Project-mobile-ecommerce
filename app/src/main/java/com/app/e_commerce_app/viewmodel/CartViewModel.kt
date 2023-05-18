package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CartRepository
import com.app.e_commerce_app.model.CartEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) :
    BaseViewModel() {
    private var _cartsData = MutableLiveData<List<CartEntity>>()
    val cartsData: LiveData<List<CartEntity>> = _cartsData

    private var _totalPrice = MutableLiveData<Long>()
    val totalPrice: LiveData<Long> = _totalPrice

    fun insertCart(cartEntity: CartEntity) = viewModelScope.launch {
        cartRepository.insertCart(cartEntity)
        getAllItems()
    }

    fun updateCart(cartEntity: CartEntity) = viewModelScope.launch(handler) {
        cartRepository.updateCart(cartEntity)
        updateTotalPrice(cartsData.value!!)
    }
//    fun deletetCart(cartModel:CartModel) = viewModelScope.launch {
//        cartRespository.deleteCart(cartModel)
//    }

    fun insertOrUpdate(cartEntity: CartEntity) = viewModelScope.launch {
        cartRepository.insertOrUpdate(cartEntity)
        getAllItems()
    }

    fun deleteCart(cartEntity: CartEntity) {
        parentJob = viewModelScope.launch(handler) {
            cartRepository.deleteCart(cartEntity)
            val response = cartRepository.getAllItems()
            updateTotalPrice(response)
            _cartsData.postValue(response)
        }
        registerJobFinish()
        getAllItems()
    }

    private fun updateTotalPrice(items: List<CartEntity>) {
        var total = 0L
        items.forEach { item ->
            total += (item.price * item.quantity)
        }
        _totalPrice.postValue(total)
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

    fun setEmpty() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            _cartsData.postValue(listOf())
        }
        registerJobFinish()
    }

//    class CartViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return CartViewModel(application) as T
//            }
//            throw IllegalArgumentException("Unable construct viewModel")
//        }
//    }
}