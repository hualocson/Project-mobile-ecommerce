package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.utils.OnCategoryIconButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment(R.layout.fragment_homepage) {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private var imageList: ArrayList<SlideModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        binding.layoutCategoryList.setAdapter(onCategoryItemButtonClick)
        binding.layoutProductList.setAdapter(onProductItemClick)
        binding.layoutCategoryIconList.setAdapter(onCategoryIconButtonClick)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageList!!.clear()
        imageList = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSlider()

        binding.layoutCategoryList.loadCategory()
        binding.layoutProductList.loadProductByCategoryId(0)
        binding.layoutCategoryIconList.loadCategory()

        val controller = findNavController()

        binding.imageProfile.setOnClickListener {
            controller.navigate(R.id.fillProfileFragment)
        }

        binding.tvSeeAllPopular.setOnClickListener {
            val bundle = bundleOf(
                "category_id" to 0
            )
            controller.navigate(R.id.storeFragment, bundle)
        }
    }

    private val onCategoryItemButtonClick: (CategoryRadioButton) -> Unit = {
        binding.layoutProductList.loadProductByCategoryId(it.id)
    }

    private val onCategoryIconButtonClick: OnCategoryIconButtonClick = {
//        binding.layoutProductList.loadProductByCategoryId(0)
    }

    private fun loadSlider() {
        if (imageList == null) {
            imageList = ArrayList()
            imageList!!.add(SlideModel(R.drawable.image1));
            imageList!!.add(SlideModel(R.drawable.image2));
            imageList!!.add(SlideModel(R.drawable.image3));
            imageList!!.add(SlideModel(R.drawable.image4));
            binding.imageSlider.setImageList(imageList!!, ScaleTypes.FIT)
        }
    }

    private val onProductItemClick: OnProductItemClick = {
        val controller = findNavController()
        val bundle = bundleOf(
            "id" to it.id
        )
        controller.navigate(R.id.productDetailFragment, bundle)
    }
}