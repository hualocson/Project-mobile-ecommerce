package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentProductDetailBinding
import com.app.e_commerce_app.model.CartEntity
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VariationOptionModel
import com.app.e_commerce_app.ui.adapter.VariationAdapter
import com.app.e_commerce_app.viewmodel.CartViewModel
import com.app.e_commerce_app.viewmodel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(inflater, container, false)
    }

    private val args by navArgs<ProductDetailFragmentArgs>()

    private val variationAdapter: VariationAdapter by lazy {
        VariationAdapter(requireContext(), onVariationClick, onVariationOptionClick)
    }

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDetailViewModel.fetchProductDetail(args.productItemId)
    }

    private fun observerEvent() {
        registerAllExceptionEvent(productDetailViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(productDetailViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(productDetailViewModel, viewLifecycleOwner)
    }

    private fun setupRecycleViewLayout() {

        binding.rvProductItemOptions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductItemOptions.adapter = variationAdapter
    }

    private fun listenHeaderClick() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.headerView.btnRight.setOnClickListener {
            navigateToPage(R.id.action_productDetailFragment_to_cartFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerEvent()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.productDetailViewModel = productDetailViewModel
        listenHeaderClick()

        var itemQty: Int = binding.tvQuantity.text.toString().toInt();

        binding.btnPlusQuantity.setOnClickListener{
            itemQty = itemQty + 1
            binding.tvQuantity.text = itemQty.toString()
        }

        binding.btnMinusQuantity.setOnClickListener{
            if(itemQty > 1){
                itemQty = itemQty - 1
            }
            binding.tvQuantity.text = itemQty.toString()
        }
        binding.imageSlider.stopSliding()
        binding.headerView.bringToFront()
        setupRecycleViewLayout()

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


        binding.btnAddToCart.setOnClickListener {
            val productId = productDetailViewModel.activeItemData.value!!.id;
            val itemName = binding.tvProductName.text.toString()
            val itemImg = productDetailViewModel.activeItemData.value!!.productImage;
            var itemDesc = ""
            productDetailViewModel.activeItemData.value!!.productConfigurations.forEach {
                item -> itemDesc = itemDesc + item.value + " "
            }
            val itemPrice = binding.tvTotalprice.text.toString()
            val itemQuantity = binding.tvQuantity.text.toString()
            val cartItem = CartEntity(productId, itemName, itemImg, itemPrice.toLong(), itemQuantity.toInt(),itemDesc)
            if(productDetailViewModel.activeItemData.value!!.id != 0){
                cartViewModel.insertOrUpdate(cartItem)
                StyleableToast.makeText(requireContext(), "Add success !", Toast.LENGTH_LONG, R.style.SuccessToast).show();
            }
            else{
                StyleableToast.makeText(requireContext(), "Please choose option for product", Toast.LENGTH_LONG, R.style.ErrorToast).show();
            }
        }
    }

    private val onVariationClick: (VariationModel) -> Unit = {
    }

    private fun activeItem(variationOption: VariationOptionModel) {
        productDetailViewModel.getProductItemId(variationOption)
        variationAdapter.setActive(variationOption.id)
    }

    private val onVariationOptionClick: (VariationOptionModel) -> Unit = {
        activeItem(it)
    }

}