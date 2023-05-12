package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.NewRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.NewJson
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val newRepository: NewRepository
) : BaseViewModel() {

    private val _newData = MutableLiveData<List<NewJson>>()
    val newData: LiveData<List<NewJson>> = _newData


    fun getAllNews() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val new = newRepository.getAllNews()!!
            _newData.postValue(new)
        }
        registerJobFinish()
    }
}