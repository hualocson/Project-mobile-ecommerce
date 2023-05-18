package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.OrderRepository
import com.app.e_commerce_app.model.UpdateOrderRequest
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.utils.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : BaseViewModel() {


    private var _orderData = MutableLiveData<List<OrderJson>>()
    val orderData: LiveData<List<OrderJson>> = _orderData

    private var _orderCommmonData = MutableLiveData<List<OrderJson>>()
    val orderCommonData: LiveData<List<OrderJson>> = _orderCommmonData

    private var _orderCompleteData = MutableLiveData<List<OrderJson>>()
    val orderCompleteData: LiveData<List<OrderJson>> = _orderCompleteData

    private var _orderPendingData = MutableLiveData<List<OrderJson>>()
    val orderPendingData: LiveData<List<OrderJson>> = _orderPendingData

    private var _orderProcessData = MutableLiveData<List<OrderJson>>()
    val orderProcessData: LiveData<List<OrderJson>> = _orderProcessData

    private var _orderCancelData = MutableLiveData<List<OrderJson>>()
    val orderCancelData: LiveData<List<OrderJson>> = _orderCancelData

    private var _activeItem = MutableLiveData<OrderJson>()
    val activeItemData: LiveData<OrderJson> = _activeItem

    private var _updateSuccess = MutableLiveData<Event<Boolean>>()
    val updateSuccess: LiveData<Event<Boolean>> = _updateSuccess

    fun fetchAllUserOrders() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val orders = orderRepository.getAllUserOrder()
            val orderComplete: ArrayList<OrderJson> = ArrayList()
            val orderCommon: ArrayList<OrderJson> = ArrayList()
            orders.forEach {
                if (it.orderStatus == "DELIVERED") {
                    orderComplete.add(it)
                } else {
                    orderCommon.add(it)
                }
            }
            _orderCommmonData.postValue(orderCommon)
            _orderCompleteData.postValue(orderComplete)
            _orderData.postValue(orders)
        }
        registerJobFinish()
    }

    fun fetchAllUserOrdersAdmin() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val orders = orderRepository.getAllUserOrder()
            val orderComplete: ArrayList<OrderJson> = ArrayList()
            val orderPending: ArrayList<OrderJson> = ArrayList()
            val orderProcess: ArrayList<OrderJson> = ArrayList()
            val orderCancel: ArrayList<OrderJson> = ArrayList()
            orders.forEach {
                when (it.orderStatus) {
                    OrderStatus.DELIVERED.value -> {
                        orderComplete.add(it)
                    }
                    OrderStatus.PENDING.value -> {
                        orderPending.add(it)
                    }
                    OrderStatus.CANCELLED.value -> {
                        orderCancel.add(it)
                    }
                    OrderStatus.PROCESSING.value -> {
                        orderProcess.add(it)
                    }
                }
            }
            _orderPendingData.postValue(orderPending)
            _orderProcessData.postValue(orderProcess)
            _orderCancelData.postValue(orderCancel)
            _orderCompleteData.postValue(orderComplete)
            _orderData.postValue(orders)
        }
        registerJobFinish()
    }

    fun getOrderById(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            parentJob = viewModelScope.launch(handler) {
                val order = orderRepository.getOrderById(id)
                _activeItem.postValue(order!!)
            }
            registerJobFinish()
        }
    }

    fun updateOrder(id: Int, updateOrderRequest: UpdateOrderRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            parentJob = viewModelScope.launch(handler) {
                val order = orderRepository.upDateOrder(id, updateOrderRequest)
                _updateSuccess.postValue(Event(true))
            }
            registerJobFinish()
        }
    }
}