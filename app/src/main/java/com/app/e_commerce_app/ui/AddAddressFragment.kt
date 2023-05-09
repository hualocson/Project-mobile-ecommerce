package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAddAddressBinding
import com.app.e_commerce_app.model.AddressJson
import com.app.e_commerce_app.model.AddressRequest
import com.app.e_commerce_app.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Address

@AndroidEntryPoint
class AddAddressFragment : BaseFragment<FragmentAddAddressBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddAddressBinding {
        return FragmentAddAddressBinding.inflate(inflater, container, false)
    }

    private val args by navArgs<AddAddressFragmentArgs>()
    private val addressViewModel: AddressViewModel by viewModels()

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateToPage(R.id.action_addAddressFragment_to_addressFragment)
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(addressViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(addressViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(addressViewModel, viewLifecycleOwner)
    }

    private fun getAddressRequestData() : AddressRequest {
        val name = binding.fillName.text.toString()
        val street = binding.fillStreet.text.toString()
        val city = binding.fillCity.text.toString()
        val state = binding.fillState.text.toString()
        val zip = binding.fillZip.text.toString()
        val addressDetails = binding.fillDetails.text.toString()
        val isDefault = binding.ckbAddress.isChecked

        if(name.isEmpty() or street.isEmpty() or city.isEmpty() or state.isEmpty() or addressDetails.isEmpty())
            showNotify("Error", "Invalid input! Please type again!!")

        return  AddressRequest(name, street, city, state,zip, addressDetails, isDefault)
    }

    private fun bindingUpdateAddress(address: AddressJson) {
        binding.fillName.setText(address.name)
        binding.fillDetails.setText(address.addressDetails)
        binding.fillCity.setText(address.city)
        binding.fillState.setText(address.state)
        binding.fillZip.setText(address.zip)
        binding.fillStreet.setText(address.street)
        binding.btnAdd.setText(R.string.update)
        binding.headerView.setTitle("Update Address")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenClickEvent()
        observerEvent()
        if(args.address == null)
            binding.btnAdd.setText(R.string.add)
        else
            bindingUpdateAddress(args.address!!)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.addressViewModel = addressViewModel

        binding.btnAdd.setOnClickListener {
            if(args.address == null) {
                val newAddress = getAddressRequestData()
                addressViewModel.addAddress(newAddress)
            }
            else {
                val updateAddress = getAddressRequestData()
                addressViewModel.updateAddress(args.address!!.id, updateAddress)
            }
        }
    }
}