package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentFinalCheckoutBinding
import com.app.e_commerce_app.model.CartModel
import com.app.e_commerce_app.ui.adapter.CartAdapter
import com.app.e_commerce_app.viewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentFinalCheckoutBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinalCheckoutBinding {
        return FragmentFinalCheckoutBinding.inflate(inflater, container, false)
    }

    private lateinit var viewStub: ViewStub

    private val args by navArgs<CheckoutFragmentArgs>()

    private val viewModel: CheckoutViewModel by viewModels()

    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(requireContext(), onItemClick, onItemDelete, itemClickCallback)
    }

    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.productList != null)
            viewModel.updateProductsData(args.productList!!.toList())

        if (args.totalPrice.isNotEmpty())
            viewModel.updateTotalPrice(args.totalPrice)
        viewModel.fetchAddresses()
        viewModel.fetchShippingMethods()
        viewModel.fetchDefaultAddress()
    }

    private fun listenClickEvent() {

        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.layoutShippingAddress.btnUpdate.setOnClickListener {
            viewModel.addresses.observe(viewLifecycleOwner) { addresses ->
                val arr = addresses!!.map { address ->
                    address.toChooseItem()
                }
                val action: NavDirections =
                    CheckoutFragmentDirections.actionCheckoutFragmentToChooseFragment(
                        items = arr.toTypedArray(),
                        chooseAddress = true
                    )
                navigateAction(action)
            }
        }
    }

    private fun listenChooseShippingClick() {
        viewStub = binding.layoutChooseShipping.viewStub!!
        val button = viewStub.inflate().findViewById<ImageButton>(R.id.btnChooseShipping)
        button.setOnClickListener {
            viewModel.shippingMethods.observe(viewLifecycleOwner) { data ->
                val arr = data!!.map { shippingMethod ->
                    shippingMethod.toChooseItem()
                }
                val action: NavDirections =
                    CheckoutFragmentDirections.actionCheckoutFragmentToChooseFragment(
                        items = arr.toTypedArray(),
                        chooseAddress = false
                    )
                navigateAction(action)
            }
        }
    }
    private fun listenShippingClick() {
        viewStub = binding.layoutShipping.viewStub!!
        val button = viewStub.inflate().findViewById<ImageButton>(R.id.btnUpdate)
        button.setOnClickListener {
            viewModel.fetchShippingMethods()
            viewModel.shippingMethods.observe(viewLifecycleOwner) { data ->
                val arr = data!!.map { shippingMethod ->
                    shippingMethod.toChooseItem()
                }
                val action: NavDirections =
                    CheckoutFragmentDirections.actionCheckoutFragmentToChooseFragment(
                        items = arr.toTypedArray(),
                        chooseAddress = false
                    )
                navigateAction(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = cartAdapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        observerEvent()
        cartAdapter.isInCheckout = true

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            viewModel.calculateTotal(0)
        }
        viewModel.shippingMethod.observe(viewLifecycleOwner) {
            viewModel.calculateTotal(it.price)
        }

        if (args.addressSelected == null && args.shippingMethodSelected == null) {
            listenChooseShippingClick()
        } else if (args.addressSelected != null && args.shippingMethodSelected == null) { //selected address
            viewModel.updateAddressSelected(args.addressSelected!!)
            if(viewModel.shippingMethod.value != null)
                listenShippingClick()
            else
                listenChooseShippingClick()
        } else if (args.addressSelected == null && args.shippingMethodSelected != null) {
            viewModel.updateShippingMethod(args.shippingMethodSelected!!)
            listenShippingClick()
        }
        else {
            Log.d("ELSE", "")
        }

        listenClickEvent()
    }


    private val itemClickCallback: (CartModel) -> Unit = {
    }

    private val onItemClick: (CartModel) -> Unit = {

    }

    private val onItemDelete: (CartModel) -> Unit = {
    }
}