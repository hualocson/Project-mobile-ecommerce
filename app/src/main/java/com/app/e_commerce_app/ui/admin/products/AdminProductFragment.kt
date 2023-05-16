package com.app.e_commerce_app.ui.admin.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminProductBinding
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.ui.admin.adapter.ItemAdminAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminProductFragment : BaseFragment<FragmentAdminProductBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminProductBinding {
        return FragmentAdminProductBinding.inflate(inflater, container, false)
    }

    private val viewModel by viewModels<AdminProductViewModel>()

    private val adapter: ItemAdminAdapter by lazy {
        ItemAdminAdapter(requireContext(), onItemClick)
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
        binding.rvProduct.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProduct.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpLayout()
        observerEvent()

        viewModel.fetchAllProducts()

        listenClickEvent()
    }

    private val onItemClick: (ChooseItem) -> Unit = {
        navigateToPage(R.id.action_adminProductFragment_to_adminEditProductFragment)
    }
}