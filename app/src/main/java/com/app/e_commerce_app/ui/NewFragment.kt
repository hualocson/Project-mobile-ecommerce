package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsBinding
import com.app.e_commerce_app.model.NewJson
import com.app.e_commerce_app.ui.adapter.NewAdapter
import com.app.e_commerce_app.viewmodel.NewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : BaseFragment<FragmentNewsBinding>(true) {

    private val newViewModel by viewModels<NewViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val newAdapter: NewAdapter by lazy {
        NewAdapter(requireContext(), onItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newViewModel = newViewModel
        observerEvent()
        setUpRecycleView()
        newViewModel.getAllNews()
    }

    private fun observerEvent() {
        registerAllExceptionEvent(newViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(newViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(newViewModel, viewLifecycleOwner)
    }

    private fun setUpRecycleView() {
        binding.rvNews.adapter = newAdapter
        binding.rvNews.layoutManager = GridLayoutManager(context, 1)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    private val onItemClick: (NewJson) -> Unit = {

    }

}
