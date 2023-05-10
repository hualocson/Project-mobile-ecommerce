package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentFinalCheckoutBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.ui.adapter.CartAdapter

class CheckoutFragment : BaseFragment<FragmentFinalCheckoutBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinalCheckoutBinding {
        return FragmentFinalCheckoutBinding.inflate(inflater, container, false)
    }

    private val args by navArgs<CheckoutFragmentArgs>()
    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(requireContext(), onItemClick, onItemDelete, itemClickCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = cartAdapter
        if(args.productList.isNotEmpty()) {
            cartAdapter.setItems(args.productList.toList())
        }

        binding.layoutShippingAddress.addressData =
            AddressJson(1, "DEMO", 1, "123", "HCM", "CD", "123124", "HCM Thu Duc", true)
    }


    private val itemClickCallback: (CartModel) -> Unit = {
    }

    private val onItemClick: (CartModel) -> Unit = {

    }

    private val onItemDelete: (CartModel) -> Unit = {
    }
}