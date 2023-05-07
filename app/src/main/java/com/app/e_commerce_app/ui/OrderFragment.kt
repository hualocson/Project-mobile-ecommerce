package com.app.e_commerce_app.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.databinding.FragmentOrderBinding
import com.app.e_commerce_app.databinding.FragmentProductDetailBinding
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.ui.adapter.CartAdapter
import com.app.e_commerce_app.viewmodel.CartViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>(false) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderBinding {
       return FragmentOrderBinding.inflate(inflater, container, false)
    }

    private val cartViewModel: CartViewModel by activityViewModels {
        CartViewModel.CartViewModelFactory(requireActivity().application)
    }

    private val cartAdapter: CartAdapter by lazy{
        CartAdapter(requireContext(), onItemClick, onItemDelete, itemClickCallback)
    }

    private fun initControls(){
        registerObserverLoadingEvent(cartViewModel, viewLifecycleOwner)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cartViewModel = cartViewModel
        binding.rvProductCart.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        handleCartLayout()
        binding.rvProductCart.adapter = cartAdapter
        cartViewModel.getAllItems()
    }

    private fun handleCartLayout(){
        if(cartViewModel.cartsData.value.isNullOrEmpty()){
            binding.flipper.showNext()
            Log.d("cart", cartViewModel.cartsData.value?.size.toString())
        }
//        if(cartAdapter.itemCount == 0){
//            binding.flipper.showNext()
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
        Log.d("cart", cartViewModel.cartsData.value?.size.toString())
    }



    private val itemClickCallback: (CartModel) -> Unit = {
        cartViewModel.updateCart(it)
    }

    private val onItemClick: (CartModel) -> Unit = {

    }

    private val onItemDelete: (CartModel) -> Unit = {
        cartViewModel.deleteCart(it)
        if(cartViewModel.cartsData.value?.size == 1){
            binding.flipper.showNext()
        }
//        Toast.makeText(requireContext(), "Delete success !", Toast.LENGTH_LONG).show()
    }
}