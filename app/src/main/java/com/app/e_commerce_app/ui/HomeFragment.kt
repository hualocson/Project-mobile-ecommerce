package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.ui.adapter.CategoryAdapter
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment(R.layout.fragment_homepage) {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private val imageList : ArrayList<SlideModel> by lazy {
        ArrayList()
    }
    private var categoryList: List<CategoryModel> = listOf()
    private val categoryAdapter : CategoryAdapter by lazy {
        CategoryAdapter(requireContext(), onCategoryItemClick)
    }

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(
            this,
            CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
        )[CategoryViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageList.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSlider()

        val controller = findNavController()
        binding.btnLogin.setOnClickListener {
            controller.navigate(R.id.loginFragment)
        }

        binding.rvCategories.layoutManager = GridLayoutManager(this.context, 4)
        binding.rvCategories.adapter = categoryAdapter

        loadCategory()

        binding.imageProfile.setOnClickListener {
            controller.navigate(R.id.fillProfileFragment)
        }
    }

    private fun loadCategory() {
        categoryViewModel.getAllCategories().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if(categoryList.isEmpty()) {
                            Log.d("Load", "Test")
                            resource.data?.let { data ->
                                categoryList = data.categories
                                categoryAdapter.setCategories(categoryList)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        Toast.makeText(requireContext(), "Category Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private val onCategoryItemClick: (CategoryModel) -> Unit = {
        Toast.makeText(requireContext(), it.categoryName, Toast.LENGTH_LONG).show()
    }
    private fun loadSlider() {
        imageList.add(SlideModel(R.drawable.image1));
        imageList.add(SlideModel(R.drawable.image2));
        imageList.add(SlideModel(R.drawable.image3));
        imageList.add(SlideModel(R.drawable.image4));
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }
}