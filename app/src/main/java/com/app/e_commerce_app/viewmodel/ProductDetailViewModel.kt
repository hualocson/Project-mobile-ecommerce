package com.app.e_commerce_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.repository.VariationRepository
import com.app.e_commerce_app.model.product.ProductDetailModel
import com.app.e_commerce_app.model.variation.VariationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : BaseViewModel() {

    private val productRepository: ProductRepository = ProductRepository()
    private val variationRepository: VariationRepository = VariationRepository()

    private val _productDetailData = MutableLiveData<ProductDetailModel>()
    val productDetailData: LiveData<ProductDetailModel> = _productDetailData

    fun fetchProductDetail(id: Int) {
        showLoading(true)
        Log.d("LOAD in PRODUCT:", "LOAD")
        parentJob = viewModelScope.launch(Dispatchers.IO) {
            val response = productRepository.getProductsById(id)
            if(response is NetWorkResult.Success) {
                val variationResponse = variationRepository.getVariationsInCategory(response.data.data!!.categoryId)

                if(variationResponse is NetWorkResult.Success) {
                    _productDetailData.postValue(ProductDetailModel(response.data.data, variationResponse.data.data!!))
                }
            }
        }
        registerJobFinish()
    }

    class ProductDetailViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}