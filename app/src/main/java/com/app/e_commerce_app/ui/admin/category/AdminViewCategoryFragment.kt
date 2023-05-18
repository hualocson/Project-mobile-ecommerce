package com.app.e_commerce_app.ui.admin.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminViewCategoryBinding
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.ui.admin.adapter.ItemAdminAdapter
import com.app.e_commerce_app.ui.admin.category.viewmodel.AdminCategoryViewModel
import com.app.e_commerce_app.ui.admin.products.AdminProductFragmentDirections
import com.app.e_commerce_app.ui.admin.products.viewmodel.AdminProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminViewCategoryFragment : BaseFragment<FragmentAdminViewCategoryBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminViewCategoryBinding {
        return FragmentAdminViewCategoryBinding.inflate(inflater, container, false)
    }


    private val viewModel by viewModels<AdminCategoryViewModel>()

    private val adapter: ItemAdminAdapter by lazy {
        ItemAdminAdapter(requireContext(), onItemClick)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.btnAdd.setOnClickListener {
            navigateToPage(R.id.action_aminViewCategoryFragment_to_adminEditCategoryFragment)
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }


    private fun setUpLayout() {
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCategories.adapter = adapter
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
        val action: NavDirections = AdminViewCategoryFragmentDirections.actionAminViewCategoryFragmentToAdminEditCategoryFragment(it.toCategoryModel())
        navigateAction(action)
    }
}