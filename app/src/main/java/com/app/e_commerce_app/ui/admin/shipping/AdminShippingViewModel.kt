package com.app.e_commerce_app.ui.admin.shipping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.ShippingRepository
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.ShippingRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class AdminShippingViewModel @Inject constructor(
    private val shippingRepository: ShippingRepository,
) : BaseViewModel() {

    private var _shippingList = MutableLiveData<List<ShippingJson>>()
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
    private val _shippingMethod = MutableLiveData<ShippingJson>()
    val shippingMethod: LiveData<ShippingJson> = _shippingMethod
    val isResponseSuccess = MutableLiveData(Event(false))
    fun addShippingMethod(shippingRequest: ShippingRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val shippingMethodNew = shippingRepository.addShippingMethod(shippingRequest)
            if(shippingMethod!=null) {
                _shippingMethod.postValue(shippingMethodNew)
//                val action: NavDirections = AdminShippingAddFragmentDirections.actionAdminShippingAddFragmentToAdminShippingFragment()
//                navigateAction(action)
                isResponseSuccess.postValue(Event(true))
            }
        }
        registerJobFinish()
    }

    fun updateShippingMethod(id: Int, shippingRequest: ShippingRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val shippingUpdatedMethod = shippingRepository.updateShippingMethod(id,shippingRequest)
            _shippingMethod.postValue(shippingUpdatedMethod)
            isResponseSuccess.postValue(Event(true))
        }
        registerJobFinish()
    }

    fun deleteShippingMethod(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            shippingRepository.deleteShippingMethod(id)
            navigateToPage(R.id.adminShippingFragment)
        }
        registerJobFinish()
    }

}