package com.app.e_commerce_app.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.app.e_commerce_app.common.EventObserver

abstract class BaseFragment<VB: ViewBinding> : Fragment() {
    private var _binding: VB? =null
    protected val binding: VB
    get() =_binding?: throw IllegalStateException("View binding is null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    protected fun showLoading(isShow: Boolean) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showLoading(isShow)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("BASEFRAGMENT ln 32", "DESTROY")
        _binding = null
    }

    protected fun registerObserverLoadingEvent(viewModel: BaseViewModel,viewLifecycleOwner: LifecycleOwner){
        viewModel.isLoading.observe(viewLifecycleOwner, EventObserver{
                isShow ->
            showLoading(isShow)
        })
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}