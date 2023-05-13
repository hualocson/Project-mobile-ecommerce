package com.app.e_commerce_app.viewmodel

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
    private val viewModelScope = CoroutineScope(Dispatchers.IO + NonCancellable)

    private var _orderSuccess = MutableLiveData<Event<Boolean>>()
    val orderSuccess: LiveData<Event<Boolean>> = _orderSuccess

    private val _addressData = MutableLiveData<AddressJson?>()
    val addressData: LiveData<AddressJson?> = _addressData

    private val _shippingMethod = MutableLiveData<ShippingJson>()
    val shippingMethod: LiveData<ShippingJson> = _shippingMethod

    private val _cartEntity = MutableLiveData<List<CartEntity>>()
    val cartEntity: LiveData<List<CartEntity>> = _cartEntity

    private val _totalPrice = MutableLiveData<Long>(0)
    val totalPrice: LiveData<Long> = _totalPrice

    private val _addresses = MutableLiveData<List<AddressJson>>()
    val addresses: LiveData<List<AddressJson>> = _addresses

    private val _shippingMethods = MutableLiveData<List<ShippingJson>>()
    val shippingMethods: LiveData<List<ShippingJson>> = _shippingMethods

    private val _sumTotal = MutableLiveData<Long>()
    val sumTotal: LiveData<Long> = _sumTotal

    init {
        _shippingMethod.observeForever { shipping ->
            _totalPrice.value?.let {
                _sumTotal.postValue(calculateTotal(it, shipping.price))
            }
        }

        _totalPrice.observeForever { amount ->
            _shippingMethod.value?.let {
                _sumTotal.postValue(calculateTotal(amount, it.price))
            }
        }
    }

    fun createOrder(
        totalPrice: Long,
        shippingMethodId: Int,
        shippingAddressId: Int,
        productItems: List<CartItemModel>
    ) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val orderLines = productItems.map { item ->
                item.toOrderLineJson()
            }

            val orderRequest =
                OrderRequest(totalPrice, shippingMethodId, shippingAddressId, orderLines)

            orderRepository.createOrder(orderRequest)
            _orderSuccess.postValue(Event(true))

        }

        registerJobFinish()
    }

    fun fetchData() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val defAdd = userRepository.getDefaultAddress()
            _addressData.postValue(defAdd)

            val addressesDeferred = async { userRepository.getAllUserAddresses() }
            val shippingMethodsDeferred = async { shippingRepository.getAllShippingMethod() }
            val listJob =
                listOf(addressesDeferred, shippingMethodsDeferred).awaitAll()

            val data1 = listJob[0] as List<*>
            val data2 = listJob[1] as List<*>

            _addresses.postValue(data1.map { it as AddressJson })
            _shippingMethods.postValue(data2.map { it as ShippingJson })
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
        _totalPrice.postValue(total)
        _sumTotal.postValue(calculateTotal(total, 0))
    }

    private fun calculateTotal(amount: Long, shippingPrice: Long): Long {
        return amount + shippingPrice
    }
}