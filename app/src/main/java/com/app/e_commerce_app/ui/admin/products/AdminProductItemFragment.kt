package com.app.e_commerce_app.ui.admin.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminProductItemBinding
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.ui.admin.adapter.ItemAdminAdapter
import com.app.e_commerce_app.ui.admin.products.viewmodel.AdminProductItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminProductItemFragment : BaseFragment<FragmentAdminProductItemBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminProductItemBinding {
        return FragmentAdminProductItemBinding.inflate(inflater, container, false)
    }

    private val viewModel by viewModels<AdminProductItemViewModel>()

    private val args by navArgs<AdminProductItemFragmentArgs>()
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
        binding.rvProductItem.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductItem.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpLayout()
        observerEvent()

        if (args.productId != 0)
            viewModel.getListProductItemInProduct(args.productId)

        listenClickEvent()
    }

    private val onItemClick: (ChooseItem) -> Unit = {

    }
}