package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.repository.VariationRepository
import com.app.e_commerce_app.model.product.ProductDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val variationRepository: VariationRepository
) :
    BaseViewModel() {

    private val _productDetailData = MutableLiveData<ProductDetailModel>()
    val productDetailData: LiveData<ProductDetailModel> = _productDetailData

//    fun fetchProductDetail(id: Int) {
//        showLoading(true)
//        parentJob = viewModelScope.launch(handler) {
//            val response = productRepository.getProductsById(id)
//            if(response is NetWorkResult.Success) {
//                val variationResponse = variationRepository.getVariationsInCategory(response.data.data!!.categoryId)
//
//                if(variationResponse is NetWorkResult.Success) {
//                    _productDetailData.postValue(ProductDetailModel(response.data.data, variationResponse.data.data!!))
//                }
//            }
//        }
//        registerJobFinish()
//    }

    fun fetchProductDetail(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val productDefer = async { productRepository.getProductsById(id) }
            val product = productDefer.await()

            val variation = variationRepository.getVariationsInCategory(product.categoryId)
            _productDetailData.postValue(ProductDetailModel(product, variation as ArrayList))
        }
        registerJobFinish()
    }
}