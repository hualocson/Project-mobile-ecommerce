package com.app.e_commerce_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.ShippingRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.ui.CheckoutFragmentDirections
import com.app.e_commerce_app.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewScoped
import dagger.internal.InjectedFieldSignature
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val shippingRepository: ShippingRepository
) : BaseViewModel() {

    private val _addressData = MutableLiveData<AddressJson>()
    val addressData: LiveData<AddressJson> = _addressData

    private val _shippingMethod = MutableLiveData<ShippingJson>()
    val shippingMethod: LiveData<ShippingJson> = _shippingMethod

    private val _productsData = MutableLiveData<List<CartModel>>()
    val productsData: LiveData<List<CartModel>> = _productsData

    private val _totalPrice = MutableLiveData<Long>()
    val totalPrice: LiveData<Long> = _totalPrice

    private val _addresses = MutableLiveData<List<AddressJson>>()
    val addresses : LiveData<List<AddressJson>> = _addresses

    private val _shippingMethods = MutableLiveData<List<ShippingJson>>()
    val shippingMethods : LiveData<List<ShippingJson>> = _shippingMethods


    private val _sumTotal = MutableLiveData<Long>()
    val sumTotal : LiveData<Long> = _sumTotal

    fun fetchDefaultAddress() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val defaultAddress = userRepository.getDefaultAddress()
            _addressData.postValue(defaultAddress)
        }
        registerJobFinish()
    }

    fun fetchAddresses() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val addresses = userRepository.getAllUserAddresses()!!
            _addresses.postValue(addresses)
        }
        registerJobFinish()
    }

    fun fetchShippingMethods() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val data = shippingRepository.getAllShippingMethod()!!
            _shippingMethods.postValue(data)
        }
        registerJobFinish()
    }

    fun updateAddressSelected(addressJson: AddressJson) {
        _addressData.postValue(addressJson)
    }
    fun updateShippingMethod(shippingJson: ShippingJson) {
        _shippingMethod.postValue(shippingJson)
    }
    fun updateProductsData(productsData: List<CartModel>) {
        _productsData.postValue(productsData)
    }

    fun updateTotalPrice(total : String) {
        parentJob = viewModelScope.launch(handler) {
            val price = total.toLong()
            _totalPrice.postValue(price)
        }
        registerJobFinish()
    }

    fun calculateTotal(shippingPrice : Long) {
        val price = totalPrice.value!! + shippingPrice
        _sumTotal.postValue(price)
    }
}