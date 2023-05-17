package com.app.e_commerce_app.ui.admin.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminEditProductItemBinding
import com.app.e_commerce_app.model.product.ProductItemRequest
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.ui.admin.adapter.VariationAdminAdapter
import com.app.e_commerce_app.ui.admin.adapter.adapterInterface.DropDownItemSelectedListener
import com.app.e_commerce_app.ui.admin.products.viewmodel.AdminEditProductItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminEditProductItemFragment : BaseFragment<FragmentAdminEditProductItemBinding>(true),
    DropDownItemSelectedListener {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminEditProductItemBinding {
        return FragmentAdminEditProductItemBinding.inflate(inflater, container, false)
    }

    private val viewModel by viewModels<AdminEditProductItemViewModel>()
    private val args by navArgs<AdminEditProductItemFragmentArgs>()
    private val adapter: VariationAdminAdapter by lazy {
        VariationAdminAdapter(requireContext())
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.btnAdd.setOnClickListener {
            if(args.productItemId != 0) {
                viewModel.productConfigurationsData.value?.let {
                    Log.d("productConfigura", it.toString())
                }
            }else {
                val quantity = binding.etQuantity.editText?.text.toString().toInt()
                val price = binding.etPrice.editText?.text.toString().toLong()
                val img = binding.etImg.editText?.text.toString().trim()
                val data = ProductItemRequest(
                    qtyInStock = quantity,
                    price = price,
                    imageUrl = img,
                    viewModel.productConfigurationsData.value!!
                )
                if (args.productId != 0)
                    viewModel.addProductItem(args.productId, data)
            }
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }

    private fun setUpLayout() {
        binding.rvVariation.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.dropdownItemSelectedListener = this
        observerData()
        binding.rvVariation.adapter = adapter
    }

    private fun observerData() {
        viewModel.itemsData.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty())
                adapter.setActiveItem(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        observerEvent()

        if (args.categoryId != 0)
            viewModel.fetchAllVariationInCategory(args.categoryId)
        if (args.productId != 0 && args.productItemId != 0) {
            viewModel.fetchProductItem(args.productId, args.productItemId)
        }

        setUpLayout()

        listenClickEvent()
    }

    override fun onItemSelected(selectedItem: VariationOptionModel) {
        viewModel.updateProductConfigurationsData(selectedItem)
    }
}