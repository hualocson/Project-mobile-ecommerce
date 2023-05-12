package com.app.e_commerce_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.CartRepository
import com.app.e_commerce_app.data.repository.OrderRepository
import com.app.e_commerce_app.data.repository.ShippingRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.CartEntity
import com.app.e_commerce_app.model.CartItemModel
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.order.OrderRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val shippingRepository: ShippingRepository,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {
    private val supervisorJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + supervisorJob)


    private var _orderSuccess = MutableLiveData<Event<Boolean>>()
    val orderSuccess: LiveData<Event<Boolean>> = _orderSuccess

    private val _addressData = MutableLiveData<AddressJson>()
    val addressData: LiveData<AddressJson> = _addressData

    private val _shippingMethod = MutableLiveData<ShippingJson>()
    val shippingMethod: LiveData<ShippingJson> = _shippingMethod

    private val _cartEntity = MutableLiveData<List<CartEntity>>()
    val cartEntity: LiveData<List<CartEntity>> = _cartEntity

    private val _totalPrice = MutableLiveData<Long>()
    val totalPrice: LiveData<Long> = _totalPrice

    private val _addresses = MutableLiveData<List<AddressJson>>()
    val addresses: LiveData<List<AddressJson>> = _addresses

    private val _shippingMethods = MutableLiveData<List<ShippingJson>>()
    val shippingMethods: LiveData<List<ShippingJson>> = _shippingMethods

    private val _sumTotal = MutableLiveData<Long>()
    val sumTotal: LiveData<Long> = _sumTotal

    fun createOrder(
        totalPrice: Long,
        shippingMethodId: Int,
        shippingAddressId: Int,
        productItems: List<CartItemModel>
    ) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            parentJob!!.ensureActive()
            val orderLines = productItems.map { item ->
                item.toOrderLineJson()
            }

            val orderRequest =
                OrderRequest(totalPrice, shippingMethodId, shippingAddressId, orderLines)

            val response = orderRepository.createOrder(orderRequest)
            _orderSuccess.postValue(Event(true))
        }

        registerJobFinish()
    }

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

    fun fetchData() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val defAddressDeferred = async { userRepository.getDefaultAddress() }
            val addressesDeferred = async { userRepository.getAllUserAddresses() }
            val shippingMethodsDeferred = async { shippingRepository.getAllShippingMethod() }

            _addressData.postValue(defAddressDeferred.await())
            _addresses.postValue(addressesDeferred.await())
            _shippingMethods.postValue(shippingMethodsDeferred.await())
        }
        registerJobFinish()
    }

    fun deleteAllCart() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            cartRepository.deleteAll()
        }
        registerJobFinish()
    }

    fun updateAddressSelected(addressJson: AddressJson) {
        _addressData.postValue(addressJson)
    }

    fun updateShippingMethod(shippingJson: ShippingJson) {
        _shippingMethod.postValue(shippingJson)
    }

    fun updateProductsData(productsData: List<CartItemModel>) {
        _cartEntity.postValue(productsData.map { it.toCartEntity() })
    }

    fun updateTotalPrice(total: Long) {
        parentJob = viewModelScope.launch(handler) {
            _totalPrice.postValue(total)
        }
        registerJobFinish()
    }

    fun calculateTotal(shippingPrice: Long) {
        val price = totalPrice.value!! + shippingPrice
        _sumTotal.postValue(price)
    }
}