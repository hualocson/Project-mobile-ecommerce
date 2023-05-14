package com.app.e_commerce_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.common.Event
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {
    private val _userLiveData = MutableLiveData<UserJson>()
    private val _checkSuccess = MutableLiveData<Boolean>()
    val userLiveData: LiveData<UserJson> = _userLiveData
    val checkSuccess: LiveData<Boolean> = _checkSuccess
    private var _uploadSuccess = MutableLiveData<Event<Boolean>>()
    val uploadSuccess: LiveData<Event<Boolean>> = _uploadSuccess
    fun checkEmail(preSignupRequest: PreSignupRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val res = userRepository.checkEmail(preSignupRequest)
            if (res.statusCode == 200) {
                _checkSuccess.postValue(true)
            }
        }
        registerJobFinish()
    }

    fun checkPassword(password: ChangePasswordRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val res = userRepository.checkPassword(password)
            if (res.statusCode == 200) {
                _checkSuccess.postValue(true)
            }
        }
        registerJobFinish()
    }

    fun register(registerRequest: RegisterRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val res = userRepository.register(registerRequest)
            navigateToPage(R.id.action_fillProfileFragment_to_loginFragment)
        }
        registerJobFinish()
    }

    fun login(loginRequest: LoginRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val token = userRepository.login(loginRequest)
            if (token.accessToken.isNotEmpty() or token.refreshToken.isNotEmpty()) {
                tokenRepository.saveToken(token)
                navigateToPage(R.id.action_loginFragment_to_homeFragment)
            }
        }
        registerJobFinish()
    }

    fun uploadImage(avatar: MultipartBody.Part){
        showLoading(true)
        parentJob = viewModelScope.launch(handler){
            val user = userRepository.uploadImage(avatar)
            _uploadSuccess.postValue(Event(true))
        }
        registerJobFinish()
    }

    fun logout() {
        tokenRepository.removeToken()
    }
    fun fetchUser() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val user = userRepository.getUserProfile()
            _userLiveData.postValue(user)
        }
        registerJobFinish()
    }
    fun setRemember(remember: Boolean) {
        tokenRepository.setRemember(remember)
    }

    fun updateProfile(userJson: UserJson) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val user = userRepository.updateUserProfile(userJson)
            _userLiveData.postValue(user)
            navigateToPage(R.id.action_fillProfileFragment_to_profileFragment)
        }
        registerJobFinish()
    }
}