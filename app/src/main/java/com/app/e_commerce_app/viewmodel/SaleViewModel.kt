package com.app.e_commerce_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.SaleRepository
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.SalesRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val saleRepository: SaleRepository
) : BaseViewModel() {

    private var _newData = MutableLiveData<List<SaleJson>>()
    val newData: LiveData<List<SaleJson>> = _newData

    private var _checkSuccess = MutableLiveData<Boolean>()
    val checkSuccess: LiveData<Boolean> = _checkSuccess

    fun getAllNews() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val new = saleRepository.getAllNews()!!
            _newData.postValue(new)
        }
        registerJobFinish()
    }

    fun createNews(salesRequest: SalesRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val new = saleRepository.createNews(salesRequest)
            if (new.statusCode == 201) {
                _checkSuccess.postValue(true)
            }
        }
        registerJobFinish()
    }

    fun updateNews(id: Int, salesRequest: SalesRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val new = saleRepository.updateNews(id, salesRequest)
            if (new.statusCode == 200) {
                _checkSuccess.postValue(true)
            }
        }
        registerJobFinish()
    }

    fun deleteNews(id: Int) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val new = saleRepository.deleteNews(id)
            if (new.statusCode == 200) {
                _checkSuccess.postValue(true)
            }
        }
        registerJobFinish()
    }
}