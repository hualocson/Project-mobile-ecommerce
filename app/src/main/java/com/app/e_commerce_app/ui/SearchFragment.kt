package com.app.e_commerce_app.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentSearchBinding
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.utils.Utils
import com.app.e_commerce_app.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(true) {

    private val searchViewModel by viewModels<SearchViewModel>()
    private var isOnPageEmpty: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val productAdapter: ProductAdapter by lazy {
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
                filterList(newText.lowercase(Locale.ROOT))
                if (newText.isEmpty())
                    binding.layoutSearchText.visibility = View.GONE
                else binding.layoutSearchText.visibility = View.VISIBLE
                binding.tvSearch.text = newText
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
        val action: NavDirections =
            SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(it.id)
        navigateAction(action)
    }

    private fun filterList(newText: String) {
        val newproductList: ArrayList<ProductModel> = ArrayList()
        searchViewModel.productsData.value!!.forEach { item ->
            if (item.name.lowercase(Locale.ROOT).contains(newText)) {
                newproductList.add(item)
            }
        }
        if (newproductList.isEmpty()) {
            if (!isOnPageEmpty) {
                binding.searchFlipper.showNext()
            }
            isOnPageEmpty = true
        } else {
            if (isOnPageEmpty) {
                binding.searchFlipper.showPrevious()
            }
            isOnPageEmpty = false
            productAdapter.setFilterList(newproductList)
        }
    }
}
