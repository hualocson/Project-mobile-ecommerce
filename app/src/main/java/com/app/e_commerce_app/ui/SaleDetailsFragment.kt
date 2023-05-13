package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentNewsFullBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaleDetailsFragment : BaseFragment<FragmentNewsFullBinding>(true) {

    private val args by navArgs<SaleDetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        if(args.saleModel != null){
            binding.newModel = args.saleModel
        }
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsFullBinding {
        return FragmentNewsFullBinding.inflate(inflater, container, false)
    }
}
