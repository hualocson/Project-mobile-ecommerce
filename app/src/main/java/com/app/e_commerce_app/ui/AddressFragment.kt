package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAddressBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.ui.adapter.AddressAdapter
import com.app.e_commerce_app.ui.adapter.VariationAdapter
import com.app.e_commerce_app.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentAddressBinding>(true) {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressBinding {
       return FragmentAddressBinding.inflate(inflater, container, false)
    }

    private val addressItemAdapter: AddressAdapter by lazy {
        AddressAdapter(requireContext(), onClick)
    }

    private val addressViewModel: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressViewModel.fetchAddresses()
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateToPage(R.id.profileFragment)
        }

        binding.btnAddAddress.setOnClickListener {
            navigateToPage(R.id.action_addressFragment_to_addAddressFragment)
        }
    }


    private fun observerEvent() {
        registerAllExceptionEvent(addressViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(addressViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(addressViewModel, viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerEvent()

        registerObserverLoadingEvent(addressViewModel, viewLifecycleOwner)
        binding.rvAddress.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAddress.adapter = addressItemAdapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.addressViewModel = addressViewModel
        listenClickEvent()
    }


    private val onClick : (AddressJson) -> Unit ={
        val action: NavDirections = AddressFragmentDirections.actionAddressFragmentToAddAddressFragment(it)
        navigateAction(action)
    }
}