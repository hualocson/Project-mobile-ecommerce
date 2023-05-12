package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentCartBinding
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.ui.adapter.CartAdapter
import com.app.e_commerce_app.viewmodel.CartViewModel

class CartFragment : BaseFragment<FragmentCartBinding>(false) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater, container, false)
    }

    private val cartViewModel: CartViewModel by activityViewModels {
        CartViewModel.CartViewModelFactory(requireActivity().application)
    }

    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(requireContext(), onItemClick, onItemDelete, itemClickCallback)
    }

    private fun initControls() {
        registerAllExceptionEvent(cartViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(cartViewModel, viewLifecycleOwner)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cartViewModel = cartViewModel
        binding.rvProductCart.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductCart.adapter = cartAdapter


        cartViewModel.setEmpty()
        cartViewModel.getAllItems()

        handleCartLayout()
    }

    private fun handleCartLayout() {
        if(!cartViewModel.cartsData.value.isNullOrEmpty()){
            binding.flipper.showNext()
            binding.btnCheckout.isEnabled = true
        }
        cartViewModel.cartsData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.flipper.showNext()
                binding.btnCheckout.isEnabled = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
        binding.btnCheckout.setOnClickListener {
            cartViewModel.cartsData.observe(viewLifecycleOwner) {
                val action =
                    CartFragmentDirections.actionCartFragmentToCheckoutFragment(cartViewModel.totalPrice.value!!)
//                CartFragmentDirections.actionCartFragmentToCheckoutFragment(it.toTypedArray(), cartViewModel.totalPrice.value!!)
                navigateAction(action)
            }
        }

        cartViewModel.cartsData.observe(viewLifecycleOwner) {
            cartAdapter.setItems(it)
        }
    }


    private val itemClickCallback: (CartModel) -> Unit = {
        cartViewModel.updateCart(it)
    }

    private val onItemClick: (CartModel) -> Unit = {

    }

    private val onItemDelete: (CartModel) -> Unit = {
        cartViewModel.deleteCart(it)
        if (cartViewModel.cartsData.value?.size == 1) {
            binding.flipper.showPrevious()
            binding.btnCheckout.isEnabled = true
        }
    }
}