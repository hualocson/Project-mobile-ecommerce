package com.app.e_commerce_app.ui.admin.shipping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.ShippingRepository
import com.app.e_commerce_app.model.ShippingJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class AdminShippingViewModel @Inject constructor(
    private val shippingRepository: ShippingRepository,
) : BaseViewModel() {

    private val _shippingList = MutableLiveData<List<ShippingJson>>()
    val shippingList: LiveData<List<ShippingJson>> = _shippingList

    fun getAllShippingMethods() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val shippingMethods = shippingRepository.getAllShippingMethod().map {
                it
            }
            _shippingList.postValue(shippingMethods)
        }
        registerJobFinish()
    }
}