package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.*
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.SaleRepository
import com.app.e_commerce_app.model.SaleJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val saleRepository: SaleRepository
) : BaseViewModel() {

    private val _newData = MutableLiveData<List<SaleJson>>()
    val newData: LiveData<List<SaleJson>> = _newData


    fun getAllNews() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val new = saleRepository.getAllNews()!!
            _newData.postValue(new)
        }
        registerJobFinish()
    }
}