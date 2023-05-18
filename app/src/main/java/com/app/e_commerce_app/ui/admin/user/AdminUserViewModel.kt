package com.app.e_commerce_app.ui.admin.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.UserJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _items = MutableLiveData<List<UserJson>>()
    val items: LiveData<List<UserJson>> = _items

    fun fetchAllUsers() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val users = userRepository.getAllUsers().map {
                it
            }
            _items.postValue(users)
        }
        registerJobFinish()
    }

//    private val _user = MutableLiveData<UserJson>()
//    val userbyId: LiveData<UserJson> = _user
//    fun getUserProfileById(id: Int) {
//        showLoading(true)
//        parentJob = viewModelScope.launch(handler) {
//            val user = userRepository.getUserProfileById(id)
//            _user.postValue(user)
//        }
//        registerJobFinish()
//    }
}