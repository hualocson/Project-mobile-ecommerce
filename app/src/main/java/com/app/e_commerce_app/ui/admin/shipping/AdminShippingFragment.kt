package com.app.e_commerce_app.ui.admin.shipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminShippingBinding
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.ui.admin.adapter.ShippingAdminAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminShippingFragment : BaseFragment<FragmentAdminShippingBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminShippingBinding {
        return FragmentAdminShippingBinding.inflate(inflater, container, false)
    }

    private val viewModel by viewModels<AdminShippingViewModel>()

    private val adapter: ShippingAdminAdapter by lazy {
        ShippingAdminAdapter(requireContext(), onItemClick)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
    }
    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }

    private fun setUpLayout() {
        binding.rvChooseItems.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvChooseItems.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.items = viewModel
        observerEvent()
        setUpLayout()
        viewModel.getAllShippingMethods()

        listenClickEvent()
    }

    private val onItemClick: (ShippingJson) -> Unit = {
        val action: NavDirections = AdminShippingFragmentDirections.actionAdminShippingFragmentToAdminShippingUpdateFragment(it)
        navigateAction(action)
    }
}