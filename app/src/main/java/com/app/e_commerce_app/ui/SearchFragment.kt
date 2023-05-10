package com.app.e_commerce_app.ui

import android.os.Bundle
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
import com.app.e_commerce_app.databinding.FragmentSearchBinding
import com.app.e_commerce_app.databinding.FragmentStoreBinding
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.ui.adapter.CartAdapter
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.OnCategoryItemButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.viewmodel.SearchViewModel
import com.app.e_commerce_app.viewmodel.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(true) {

    private val searchViewModel by viewModels<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val productAdapter: ProductAdapter by lazy{
        ProductAdapter(requireContext(), onProductItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchViewModel = searchViewModel
        binding.searchView.clearFocus()
        observerEvent()
        setUpRecycleView()
        searchViewModel.fetchAllProducts()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText.toLowerCase())
                return false
            }
        })
    }

    private fun observerEvent() {
        registerAllExceptionEvent(searchViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(searchViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(searchViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.layoutProductList.adapter = productAdapter
        binding.layoutProductList.layoutManager = GridLayoutManager(context, 2)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }


    private val onProductItemClick: OnProductItemClick = {
        val controller = findNavController()
        val bundle = bundleOf(
            "id" to it.id
        )
        controller.navigate(R.id.productDetailFragment, bundle)
    }

    private fun filterList(newText: String){
        val newproductList: ArrayList<ProductModel> = ArrayList()
        searchViewModel.productsData.value!!.forEach {
            item -> if(item.name.toLowerCase().contains(newText)){
                newproductList.add(item)
        }
        }
        if(newproductList.isEmpty()){
            // Handle Empty
        }
        else{
            productAdapter.setFilterList(newproductList)
        }
    }
}
