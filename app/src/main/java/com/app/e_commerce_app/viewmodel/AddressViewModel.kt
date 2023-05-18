package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.AddressRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {


    private var _addressesData = MutableLiveData<List<AddressJson>>()
    val addressesData: LiveData<List<AddressJson>> = _addressesData

    fun fetchAddresses() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val addresses = userRepository.getAllUserAddresses()
            _addressesData.postValue(addresses!!)
        }
        registerJobFinish()
    }

    fun addAddress(addressRequest: AddressRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val address = userRepository.addAddress(addressRequest)
            if (address != null)
                navigateToPage(R.id.action_addAddressFragment_to_addressFragment)
        }
        registerJobFinish()
    }

    fun updateAddress(addressId: Int, addressRequest: AddressRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val address = userRepository.updateAddress(addressId, addressRequest)
            if (address != null) {
                navigateToPage(R.id.action_addAddressFragment_to_addressFragment)
            }
        }
        registerJobFinish()
    }
}