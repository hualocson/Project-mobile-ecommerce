package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentFillProfileBinding
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.utils.Gender
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillProfileFragment : BaseFragment<FragmentFillProfileBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFillProfileBinding {
        return FragmentFillProfileBinding.inflate(inflater, container, false)
    }

    private val args by navArgs<FillProfileFragmentArgs>()
    private val userViewModel: UserViewModel by viewModels()
    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (args.isLogged)
            userViewModel.fetchUser()
    }

    private fun observerEvent() {
        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenClickEvent()
        observerEvent()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        //Prepare value for gender menu
        val genderValues = resources.getStringArray(R.array.gender_values)
        val adapter = ArrayAdapter(requireContext(), R.layout.gender_list_item, genderValues)
        val autoCompleteTextView = (binding.genderMenu.editText as? AutoCompleteTextView)
        autoCompleteTextView?.setAdapter(adapter)
        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            autoCompleteTextView.setText(selectedItem, false)
        }
        if (args.isLogged) {
            userViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    binding.fillFirstname.setText(user.firstName)
                    binding.fillLastname.setText(user.lastName)
                    binding.fillPhone.setText(user.phone)
                    autoCompleteTextView?.setText(user.gender.value, false)
                }
            }
            binding.btnFill.setText(R.string.update)
        } else {
            autoCompleteTextView?.setText(adapter.getItem(3).toString(), false)
        }


        //Handle click event for fill button
        binding.btnFill.setOnClickListener() {
            //Check null for all binding fields
            if (binding.fillFirstname.text.toString()
                    .isEmpty() || binding.fillLastname.text.toString()
                    .isEmpty() || binding.fillPhone.text.toString().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please enter all information", Toast.LENGTH_LONG)
                    .show()
            } else {
                if (args.isLogged) {
                    updateProfile()
                } else {
                    register()
                }
            }
        }

        binding.btnUpload.setOnClickListener {
            navigateToPage(R.id.uploadFragment)
        }
    }

    private fun updateProfile() {
        //Get email and password from previous fragment
        //Prepare request body
        val updateProfileRequest = UserJson(
            0,
            "",
            binding.fillFirstname.text.toString(),
            binding.fillLastname.text.toString(),
            binding.fillPhone.text.toString(),
            "",
            Gender.valueOf(binding.genderMenu.editText?.text.toString())
        )
        userViewModel.updateProfile(updateProfileRequest)
    }

    private fun register() {
        //Get email and password from previous fragment
        val signupData = args.signupData!!
        //Prepare request body
        val registerRequest = RegisterRequest(
            signupData.email,
            signupData.password,
            "",
            binding.fillFirstname.text.toString(),
            binding.fillLastname.text.toString(),
            binding.fillPhone.text.toString(),
            binding.genderMenu.editText?.text.toString(),
        )
        userViewModel.register(registerRequest)
    }
}