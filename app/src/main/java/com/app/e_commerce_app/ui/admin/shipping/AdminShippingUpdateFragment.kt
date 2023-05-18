package com.app.e_commerce_app.ui.admin.shipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentAdminShippingUpdateBinding
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.ShippingRequest
import com.app.e_commerce_app.ui.admin.adapter.CustomDropdownAdapter
import com.app.e_commerce_app.ui.admin.adapter.ShippingAdminAdapter
import com.app.e_commerce_app.ui.admin.products.AdminEditProductFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminShippingUpdateFragment : BaseFragment<FragmentAdminShippingUpdateBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminShippingUpdateBinding {
        return FragmentAdminShippingUpdateBinding.inflate(inflater, container, false)
    }
    private val viewModel by viewModels<AdminShippingViewModel>()
    private val args by navArgs<AdminShippingUpdateFragmentArgs>()
    private fun setUpLayout() {
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observerEvent()

        //Đổ dữ liệu vào từ args truyền qua
        binding.shippingJson = args.shippingJson
        viewModel.isResponseSuccess.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess)
                navigateBack()
        })
        setUpLayout()
        listenClickEvent()
    }

//    private val onItemClick: (ShippingJson) -> Unit = {
//        val action: NavDirections = AdminShippingFragmentDirections.actionAdminShippingFragmentToAdminShippingUpdateFragment(it)
//        navigateAction(action)
//    }
    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
        binding.btnUpdate.setOnClickListener {
            val shippingRequest = ShippingRequest(
                name = binding.edtName.text.toString(),
                price = binding.edtPrice.text.toString().toLong(),
                desc = binding.edtDesc.text.toString()
            )
            viewModel.updateShippingMethod(args.shippingJson!!.id, shippingRequest)
        }
        binding.btnDelete.setOnClickListener {
            args.shippingJson?.id?.let { it1 -> viewModel.deleteShippingMethod(it1) }
        }
    }
    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }
}