package com.app.e_commerce_app.viewmodel

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.e_commerce_app.base.BaseViewModel
import com.app.e_commerce_app.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ValidateViewModel @Inject constructor() : BaseViewModel() {
    private var _checkEmail = MutableLiveData<Boolean>(true)
    val checkEmail: LiveData<Boolean> = _checkEmail

    val emailFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            val editText = view as EditText
            _checkEmail.postValue(Utils.validateEmail(editText.text.toString()))
        }
    }

    fun onEmailChanged(
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        val email = text?.toString()
        if (email.isNullOrEmpty())
            _checkEmail.postValue(true)
    }

}