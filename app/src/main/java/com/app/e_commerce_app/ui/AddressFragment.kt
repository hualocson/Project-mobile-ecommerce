package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAddressBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.ui.adapter.AddressAdapter
import com.app.e_commerce_app.ui.adapter.VariationAdapter
import com.app.e_commerce_app.viewmodel.AddressViewModel

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

    private val addressViewModel: AddressViewModel by viewModels {
        AddressViewModel.AddressViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressViewModel.fetchAddresses()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserverLoadingEvent(addressViewModel, viewLifecycleOwner)
        binding.rvAddress.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAddress.adapter = addressItemAdapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.addressViewModel = addressViewModel

        binding.headerView.btnLeft.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    val onClick : (AddressJson) -> Unit ={

    }
}