package com.app.e_commerce_app.ui.admin.shipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentAdminShippingAddBinding
import com.app.e_commerce_app.model.ShippingRequest
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class AdminShippingAddFragment : BaseFragment<FragmentAdminShippingAddBinding>(true){
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminShippingAddBinding {
        return FragmentAdminShippingAddBinding.inflate(inflater, container, false)
    }

    private val viewModel by viewModels<AdminShippingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.items = viewModel
        observerEvent()
        listenClickEvent()
        viewModel.isResponseSuccess.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess)
                navigateBack()
        })
    }
    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
        binding.btnAdd.setOnClickListener {
            if (binding.fillName.text.toString().isEmpty() || binding.fillPrice.text.toString().isEmpty() || binding.fillImgUrl.text.toString().isEmpty() || binding.fillDesc.text.toString().isEmpty())
            {
                StyleableToast.makeText(requireContext(), "Please enter all information", R.style.ErrorToast).show()
            }
            else
            {
                val name = binding.fillName.text.toString()
                val price = binding.fillPrice.text.toString()
                val iconUrl = binding.fillImgUrl.text.toString()
                val desc = binding.fillDesc.text.toString()
                val shippingRequest = ShippingRequest(
                    name = name,
                    price = price.toLong(),
                    icon = iconUrl,
                    desc = desc
                )
                viewModel.addShippingMethod(shippingRequest)
            }
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }

//    private fun setUpLayout() {
//        binding.rvChooseItems.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvChooseItems.adapter = adapter
//    }
}