package com.app.e_commerce_app.ui.admin.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAdminViewProfileBinding
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.ui.FillProfileFragmentArgs
import com.app.e_commerce_app.ui.admin.adapter.UserAdminAdapter

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminViewUserProfileFragment : BaseFragment<FragmentAdminViewProfileBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdminViewProfileBinding {
        return FragmentAdminViewProfileBinding.inflate(inflater, container, false)
    }
    private val args by navArgs<AdminViewUserProfileFragmentArgs>()
    private val viewModel by viewModels<AdminUserViewModel>()

    private val adapter: UserAdminAdapter by lazy {
        UserAdminAdapter(requireContext(), onItemClick)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
    }
    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }

//    private fun setUpLayout() {
//        binding.rvUser.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvUser.adapter = adapter
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userJson = args.userById
        observerEvent()
//        setUpLayout()
        val userJson = args.userById
        listenClickEvent()
    }

    private val onItemClick: (UserJson) -> Unit = {
//        navigateToPage(R.id.action_adminUserFragment_to_adminEditUserFragment)
    }
}