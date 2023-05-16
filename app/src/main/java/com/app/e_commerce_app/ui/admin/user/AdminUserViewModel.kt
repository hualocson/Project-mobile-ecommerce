package com.app.e_commerce_app.ui.admin.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.CategoryRepository
import com.app.e_commerce_app.data.repository.ProductRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _items = MutableLiveData<List<ChooseItem>>()
    val items: LiveData<List<ChooseItem>> = _items

    fun fetchAllUsers() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val users = userRepository.getAllUsers().map {
                it.toAdminItem()
            }
            _items.postValue(users)
        }
        registerJobFinish()
    }
}