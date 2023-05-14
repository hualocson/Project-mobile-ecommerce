package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentChooseBinding
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.ui.adapter.ChooseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseFragment : BaseFragment<FragmentChooseBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChooseBinding {
        return FragmentChooseBinding.inflate(inflater, container, false)
    }

    private val args by navArgs<ChooseFragmentArgs>()
    private val chooseAdapter: ChooseAdapter by lazy {
        ChooseAdapter(requireContext(), onItemClick)
    }
    private fun listenClickEvent() {
        binding.btnApply.setOnClickListener {
            val data = chooseAdapter.getActiveItem()

            val action: NavDirections = if(args.chooseAddress){
                ChooseFragmentDirections.actionChooseFragmentToCheckoutFragment(addressSelected = data.toAddressJson())
            } else {
                ChooseFragmentDirections.actionChooseFragmentToCheckoutFragment(shippingMethodSelected = data.toShippingJson())
            }
            navigateAction(action)
        }
    }
    private fun setUpLayout() {
        binding.rvChooseItems.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvChooseItems.adapter = chooseAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()

        if (args.items.isNotEmpty())
            binding.items = args.items.toList()

        if(args.chooseAddress)
            binding.headerView.setTitle("Shipping Address")
        else
            binding.headerView.setTitle("Choose Shipping")

        listenClickEvent()
    }

    private val onItemClick: (ChooseItem) -> Unit = {

    }
}