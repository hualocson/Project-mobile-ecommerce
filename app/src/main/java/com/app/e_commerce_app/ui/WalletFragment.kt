package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment(R.layout.fragment_wallet){
    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}