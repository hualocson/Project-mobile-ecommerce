package com.app.e_commerce_app.ui.admin.order.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.OrderRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.UpdateOrderRequest
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderLineJson
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.utils.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _productItemData = MutableLiveData<List<ProductItemJson>>()
    val productItemData: LiveData<List<ProductItemJson>> = _productItemData


    fun getProductItem(list: List<OrderLineJson>) {
        showLoading(true)
            parentJob = viewModelScope.launch(handler) {
                val productItemList: ArrayList<ProductItemJson> =  ArrayList()
                list.forEach {
                    val item = productRepository.getProductsItems(it.productItemId)
                    productItemList.add(item)
                }
                _productItemData.postValue(productItemList)
            }
        registerJobFinish()
    }

}