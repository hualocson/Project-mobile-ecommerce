package com.app.e_commerce_app.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.dialogs.ConfirmDialog
import com.app.e_commerce_app.base.network.BaseNetworkException
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment<VB : ViewBinding>(private val isHideBottomNavigationView: Boolean) :
    Fragment() {
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("View binding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        if (isHideBottomNavigationView) {
            Log.d("HIDE", "BOTTOM")
            requireActivity().findViewById<BottomNavigationView>(R.id.navigation_view).visibility =
                View.GONE
        } else requireActivity().findViewById<BottomNavigationView>(R.id.navigation_view).visibility =
            View.VISIBLE
        return binding.root
    }

    protected fun navigateToPage(actionId: Int) {
        findNavController().navigate(actionId)
    }

    protected fun navigateBack() {
        findNavController().popBackStack()
    }

    protected fun navigateAction(action: NavDirections) {
        findNavController().navigate(action)
    }


    protected fun showErrorMessage(e: BaseNetworkException) {
        showErrorMessage(e.mainMessage)
    }

    protected fun showErrorMessage(messageId: Int) {
        val message = requireContext().getString(messageId)
        showErrorMessage(message)
    }

    protected fun showErrorMessage(message: String) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showErrorDialog(message)
        }
    }

    protected fun showNotify(title: String?, message: String, status: Status = Status.ERROR) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showNotifyDialog(title ?: getDefaultNotifyTitle(), message, "OK", status)
        }
    }

    protected fun showNotify(titleId: Int = R.string.default_notify_title, messageId: Int) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showNotifyDialog(titleId, messageId)
        }
    }

    protected fun showConfirm(
        title: String, message: String?, positiveButtonTitle: String,
        negativeButtonTitle: String,
        callback: ConfirmDialog.ConfirmCallback
    ) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showConfirmDialog(
                title,
                message,
                positiveButtonTitle,
                negativeButtonTitle,
                callback
            )
        }
    }

    protected fun registerAllExceptionEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        registerObserverExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverNetworkExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverMessageEvent(viewModel, viewLifecycleOwner)
    }

    protected fun registerObserverExceptionEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.baseNetworkException.observe(viewLifecycleOwner, EventObserver {
            showErrorMessage(it)
        })
    }

    protected fun registerObserverNetworkExceptionEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.networkException.observe(viewLifecycleOwner, EventObserver {
            showNotify(getDefaultNotifyTitle(), it.responseMessage ?: "Network error")
        })
    }

    protected fun registerObserverMessageEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.errorMessageResourceId.observe(viewLifecycleOwner, EventObserver { message ->
            showErrorMessage(message)
        })
    }

    protected fun registerObserverNavigateEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.onNavigateToPage.observe(viewLifecycleOwner, EventObserver {
            navigateToPage(it)
        })
        viewModel.onNavigateAction.observe(viewLifecycleOwner, EventObserver {
            navigateAction(it)
        })
    }

    protected fun showLoading(isShow: Boolean) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showLoading(isShow)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun registerObserverLoadingEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.isLoading.observe(viewLifecycleOwner, EventObserver { isShow ->
            showLoading(isShow)
        })
    }

    private fun getDefaultNotifyTitle(): String {
        return getString(R.string.default_notify_title)
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}