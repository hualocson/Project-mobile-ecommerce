package com.app.e_commerce_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.e_commerce_app.common.Event
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Event<Boolean>>()
        protected set

    var parentJob: Job? = null
        protected set

    protected fun registerJobFinish() {
        parentJob?.invokeOnCompletion {
            showLoading(false)
        }
    }

    protected fun showLoading(isShow: Boolean) {
        isLoading.postValue(Event(isShow))
    }
}