package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) :
    BaseViewModel() {

    private var _productDetailData = MutableLiveData<ProductModel>()
    val productDetailData: LiveData<ProductModel> = _productDetailData

    private var _activeItem = MutableLiveData<ProductItemJson>()
    val activeItemData: LiveData<ProductItemJson> = _activeItem

    private var _totalPrice = MutableLiveData<Long>(0)
    val totalPrice: LiveData<Long> = _totalPrice

    fun getProductItemId(variationOption: VariationOptionModel) {
        val data = _productDetailData.value!!.productItems!!.find { it.id == variationOption.id }
        data?.let {
            _activeItem.postValue(it)
            _totalPrice.postValue(it.price)
        }
    }

    fun updatePrice(qty: Int) {
        _activeItem.value?.let {
            _totalPrice.postValue(qty * it.price)
        }
    }

    fun fetchProductDetail(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val product = productRepository.getProductsById(id)
            _productDetailData.postValue(product)
            _activeItem.postValue(
                ProductItemJson(
                    productImage = product.productImage,
                    price = product.minPrice,
                    productConfigurations = ArrayList()
                )
            )
            _totalPrice.postValue(product.minPrice)
        }
        registerJobFinish()
    }
}