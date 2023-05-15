package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentStoreBinding
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.OnCategoryItemButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.viewmodel.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : BaseFragment<FragmentStoreBinding>(true) {

    private var categoryId: Int = 0
    private val args by navArgs<StoreFragmentArgs>()
    private val storeViewModel by viewModels<StoreViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.storeViewModel = storeViewModel
        observerEvent()
        setUpRecycleView()

        categoryId = args.categoryId

        if (categoryId == 0) {
            binding.storeHeader.setTitle("Most Popular")
        }

        storeViewModel.getProductsByCategory(categoryId)

        binding.storeHeader.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.storeHeader.btnRight.setOnClickListener {
            navigateToPage(R.id.action_storeFragment_to_searchFragment)
        }

    }

    private fun observerEvent() {
        registerAllExceptionEvent(storeViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(storeViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(storeViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.layoutProductList.adapter = ProductAdapter(requireContext(), onProductItemClick)
        binding.layoutProductList.layoutManager = GridLayoutManager(context, 2)

        binding.layoutCategoryList.adapter =
            CategoryButtonAdapter(requireContext(), onCategoryItemButtonClick)
        binding.layoutCategoryList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStoreBinding {
        return FragmentStoreBinding.inflate(inflater, container, false)
    }

    private val onCategoryItemButtonClick: OnCategoryItemButtonClick = { radioButton ->
        storeViewModel.getProductsByCategory(radioButton.id)
    }

    private val onProductItemClick: OnProductItemClick = {
        val action : NavDirections = StoreFragmentDirections.actionStoreFragmentToProductDetailFragment(it.id)
        navigateAction(action)
    }
}