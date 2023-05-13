package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.ui.adapter.CategoryAdapter
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.ui.adapter.ProductAdapter
import com.app.e_commerce_app.utils.OnCategoryIconButtonClick
import com.app.e_commerce_app.utils.OnProductItemClick
import com.app.e_commerce_app.viewmodel.HomeViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomepageBinding>(false) {

    private var imageList: ArrayList<SlideModel>? = null

    private val homeViewModel: HomeViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomepageBinding {
        return FragmentHomepageBinding.inflate(inflater, container, false)
    }

    private fun observerEvent() {
        registerAllExceptionEvent(homeViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(homeViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(homeViewModel, viewLifecycleOwner)
    }

    private fun setupRecycleViewLayout() {
        binding.rvCategoryIcon.adapter =
            CategoryAdapter(requireContext(), onCategoryIconClick)
        binding.rvCategoryIcon.layoutManager = GridLayoutManager(context, 4)

        binding.rvProducts.adapter = ProductAdapter(requireContext(), onProductItemClick)
        binding.rvProducts.layoutManager = GridLayoutManager(context, 2)

        binding.layoutCategoryList.adapter =
            CategoryButtonAdapter(requireContext(), onCategoryItemButtonClick)
        binding.layoutCategoryList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSlider()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = homeViewModel
        observerEvent()
        setupRecycleViewLayout()

        if (!homeViewModel.checkIsLogin())
            navigateToPage(R.id.action_homeFragment_to_loginFragment)
        else {
            homeViewModel.fetchUser()
            homeViewModel.fetchData()
        }

        val controller = findNavController()


        binding.inputSearchHome.setOnClickListener {
            controller.navigate(R.id.searchFragment)
        }

        binding.tvSeeAllPopular.setOnClickListener {
            val bundle = bundleOf(
                "category_id" to 0
            )
            controller.navigate(R.id.storeFragment, bundle)
        }
        binding.tvSeeAllSpecial.setOnClickListener {
            navigateToPage(R.id.saleFragment)
        }
    }

    private val onCategoryItemButtonClick: (CategoryRadioButton) -> Unit = {
        homeViewModel.fetchProductsByCategoryId(it.id)
        homeViewModel.isProductsLoading.observe(viewLifecycleOwner, EventObserver { isShow ->
            if (isShow)
                binding.productsLoadingLayout.visibility = View.VISIBLE
            else
                binding.productsLoadingLayout.visibility = View.GONE
        })
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
        val action : NavDirections = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(it.id)
        navigateAction(action)
    }

    private val onCategoryIconClick: (CategoryModel) -> Unit = {

    }
}