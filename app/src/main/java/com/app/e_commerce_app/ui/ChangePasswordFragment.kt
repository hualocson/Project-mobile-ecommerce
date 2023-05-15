package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentChangePasswordBinding
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.model.ChangePasswordRequest
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.ui.adapter.SaleAdapter
import com.app.e_commerce_app.viewmodel.SaleViewModel
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(true) {

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        observerEvent()
        binding.btnChange.setOnClickListener {
            //Check null for all fields
            if (binding.fillOldpwd.text.toString().isEmpty() || binding.fillNewpwd.text.toString()
                    .isEmpty() || binding.fillCfm.text.toString().isEmpty()
            ) {
                StyleableToast.makeText(requireContext(), "Please enter all information", R.style.ErrorToast).show()
            }
            else {
                //Check password and confirm password
                if (binding.fillNewpwd.text.toString() != binding.fillCfm.text.toString()) {
                    StyleableToast.makeText(requireContext(), "Password news not match", R.style.ErrorToast).show()
                } else {
                    checkPassword()
                }
            }
        }
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangePasswordBinding {
        return FragmentChangePasswordBinding.inflate(inflater, container, false)
    }

    private fun checkPassword()
    {
        val changePasswordRequest = ChangePasswordRequest(
            binding.fillOldpwd.text.toString(),
            binding.fillNewpwd.text.toString()
        )

        userViewModel.checkPassword(changePasswordRequest)
        userViewModel.checkSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                val action: NavDirections = ChangePasswordFragmentDirections.actionChangePasswordFragmentToProfileFragment()
                navigateAction(action)
            }
        }
    }

}
