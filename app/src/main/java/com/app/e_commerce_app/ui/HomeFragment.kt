package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.utils.OnCategoryIconButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.viewmodel.UserViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : BaseFragment<FragmentHomepageBinding>() {

    private var imageList: ArrayList<SlideModel>? = null

    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModel.UserViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!userViewModel.checkIsLogin())
            navigateToPage(R.id.action_homeFragment_to_loginFragment)
        else
            userViewModel.fetchUser()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomepageBinding {
        return FragmentHomepageBinding.inflate(inflater, container, false)
    }

    private fun observerEvent() {
        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)

        registerAllExceptionEvent(binding.layoutCategoryIconList.categoryViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(binding.layoutCategoryIconList.categoryViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(binding.layoutCategoryIconList.categoryViewModel, viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSlider()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        observerEvent()

        binding.layoutCategoryList.setAdapter(onCategoryItemButtonClick)
        binding.layoutProductList.setAdapter(onProductItemClick)
        binding.layoutCategoryIconList.setAdapter(onCategoryIconButtonClick)

        binding.layoutCategoryList.loadCategory()
        binding.layoutProductList.loadProductByCategoryId(0)
        binding.layoutCategoryIconList.loadCategory()


        val controller = findNavController()


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
        } else
            imageList!!.clear()

        imageList!!.add(SlideModel(R.drawable.image1));
        imageList!!.add(SlideModel(R.drawable.image2));
        imageList!!.add(SlideModel(R.drawable.image3));
        imageList!!.add(SlideModel(R.drawable.image4));
        binding.imageSlider.setImageList(imageList!!, ScaleTypes.FIT)
    }

    private val onProductItemClick: OnProductItemClick = {
        val controller = findNavController()
        val bundle = bundleOf(
            "id" to it.id
        )
        controller.navigate(R.id.productDetailFragment, bundle)
    }
}