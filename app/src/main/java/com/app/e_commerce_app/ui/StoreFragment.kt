package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    private val storeViewModel by viewModels<StoreViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.storeViewModel = storeViewModel
        observerEvent()
        setUpRecycleView()


        val bundle = arguments
        categoryId = bundle!!.getInt("category_id")

        if (categoryId == 0) {
            binding.storeHeader.setTitle("Most Popular")
        }
//        binding.layoutProductList.loadProductByCategoryId(categoryId)
//        binding.layoutCategoryList.loadCategory(categoryId)
        storeViewModel.getProductsByCategory(categoryId)
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
        val controller = findNavController()
        val bundle = bundleOf(
            "id" to it.id
        )
        controller.navigate(R.id.productDetailFragment, bundle)
    }
}