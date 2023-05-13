package com.app.e_commerce_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.OrderRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : BaseViewModel() {


    private val _orderData = MutableLiveData<List<OrderJson>>()
    val orderData: LiveData<List<OrderJson>> = _orderData

    private val _orderCommmonData = MutableLiveData<List<OrderJson>>()
    val orderCommonData: LiveData<List<OrderJson>> = _orderCommmonData

    private val _orderCompleteData = MutableLiveData<List<OrderJson>>()
    val orderCompleteData: LiveData<List<OrderJson>> = _orderCompleteData


    fun fetchAllUserOrders(){
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val orders = orderRepository.getAllUserOrder()
            Log.d("order", orders.toString())
            val orderComplete: ArrayList<OrderJson> = ArrayList()
            val orderCommon: ArrayList<OrderJson> = ArrayList()
            orders.forEach {
                if(it.orderStatus == "DELIVERED"){
                    orderComplete.add(it)
                }
                else {
                    orderCommon.add(it)
                }
            }
            _orderCommmonData.postValue(orderCommon)
            _orderCompleteData.postValue(orderComplete)
            Log.d("common", orderCommon.toString())
            Log.d("complete", orderComplete.toString())
            _orderData.postValue(orders)
        }
        registerJobFinish()
    }


}