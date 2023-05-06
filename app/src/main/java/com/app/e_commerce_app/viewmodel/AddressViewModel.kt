package com.app.e_commerce_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.AddressJson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {


    private val _addressesData = MutableLiveData<List<AddressJson>>()
    val addressesData: LiveData<List<AddressJson>> = _addressesData


//    fun fetchAllAddresses() = liveData(Dispatchers.IO) {
//        showLoading(true)
//        emit(Resource.loading(null))
//        when (val response = userRepository.getAllUserAddresses()) {
//            is NetWorkResult.Success -> {
//                response.data.data.let { addresses ->
//                    emit(Resource.success(addresses))
//                    _addressesData.postValue(addresses)
//                }
//            }
//            is NetWorkResult.Error -> emit(Resource.error(null, response.message))
//            is NetWorkResult.Exception -> emit(Resource.error(null, response.e.message))
//        }
//        registerJobFinish()
//    }

    fun fetchAddresses() {
//        showLoading(true)
//        parentJob = viewModelScope.launch(Dispatchers.IO) {
//            val response = userRepository.getAllUserAddresses()
//            if (response is NetWorkResult.Success) {
//                _addressesData.postValue(response.data.data!!)
//            }
//        }
//        registerJobFinish()
    }
}