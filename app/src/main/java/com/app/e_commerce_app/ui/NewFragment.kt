package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.databinding.FragmentSearchBinding
import com.app.e_commerce_app.databinding.FragmentStoreBinding
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.model.NewJson
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.ui.adapter.CartAdapter
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.ui.adapter.NewAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.OnCategoryItemButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.utils.Resource
import com.app.e_commerce_app.viewmodel.NewViewModel
import com.app.e_commerce_app.viewmodel.SearchViewModel
import com.app.e_commerce_app.viewmodel.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : BaseFragment<FragmentNewsBinding>(true) {

    private val newViewModel by viewModels<NewViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val newAdapter: NewAdapter by lazy{
        NewAdapter(requireContext(), onItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newViewModel = newViewModel
        observerEvent()
        setUpRecycleView()
        newViewModel.getAllNews()
    }

    private fun observerEvent() {
        registerAllExceptionEvent(newViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(newViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(newViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvNews.adapter = newAdapter
        binding.rvNews.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    private val onItemClick: (NewJson) -> Unit = {

    }

}
