package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.databinding.FragmentLoginBinding
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.UserViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment(R.layout.fragment_homepage) {

    private var _binding : FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private val imagelist = ArrayList<SlideModel>()

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            UserViewModel.UserViewModelFactory(requireActivity().application)
        )[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSlider()
    }
    fun loadSlider(){
        imagelist.add(SlideModel(R.drawable.image1));
        imagelist.add(SlideModel(R.drawable.image2));
        imagelist.add(SlideModel(R.drawable.image3));
        imagelist.add(SlideModel(R.drawable.image4));
        binding.imageSlider.setImageList(imagelist, ScaleTypes.FIT)
    }
}