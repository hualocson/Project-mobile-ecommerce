package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentProductDetailBinding
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.ui.adapter.VariationAdapter
import com.app.e_commerce_app.viewmodel.ProductDetailViewModel

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(inflater, container, false)
    }

    private val variationAdapter: VariationAdapter by lazy {
        VariationAdapter(requireContext(), onVariationClick)
    }

    private val productDetailViewModel: ProductDetailViewModel by activityViewModels {
        ProductDetailViewModel.ProductDetailViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        val id = bundle!!.getInt("id")
        productDetailViewModel.fetchProductDetail(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserverLoadingEvent(productDetailViewModel, viewLifecycleOwner)

        binding.imageSlider.stopSliding()
        binding.headerView.bringToFront()



        binding.rvProductItemOptions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductItemOptions.adapter = variationAdapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.productDetailViewModel = productDetailViewModel


        binding.layoutDesc.setOnClickListener {
            if (binding.tvDescShort.visibility == View.VISIBLE) {
                binding.tvDescShort.visibility = View.GONE
                binding.tvDescFull.visibility = View.VISIBLE
                binding.tvDescFull.maxLines = Integer.MAX_VALUE
            } else {
                binding.tvDescShort.visibility = View.VISIBLE
                binding.tvDescFull.visibility = View.GONE
                binding.tvDescFull.maxLines = 2
            }
        }
    }

    private val onVariationClick: (VariationModel) -> Unit= {}


    private val onVariationOptionClick: (VariationOptionModel) -> Unit= {}

}