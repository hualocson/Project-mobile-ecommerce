package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentProductDetailBinding
import com.app.e_commerce_app.model.ProductModel
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.ProductViewModel
import com.denzcoskun.imageslider.models.SlideModel

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by activityViewModels {
        ProductViewModel.ProductViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.imageSlider.stopSliding()
        binding.headerView.bringToFront()
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val id = bundle!!.getInt("id")
        Log.d("ID:::", id.toString())
        loadProductDetail(id)

        binding.layoutDesc.setOnClickListener {
            if(binding.tvDescShort.visibility == View.VISIBLE) {
                binding.tvDescShort.visibility = View.GONE
                binding.tvDescFull.visibility = View.VISIBLE
                binding.tvDescFull.maxLines = Integer.MAX_VALUE
            }
            else {
                binding.tvDescShort.visibility = View.VISIBLE
                binding.tvDescFull.visibility = View.GONE
                binding.tvDescFull.maxLines = 2
            }
        }
    }

    private fun setData(product: ProductModel) {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(product.productImage))

        product.productItems?.forEach {
            imageList.add(SlideModel(it.productImage))
        }

        binding.imageSlider.setImageList(imageList)
        binding.tvProductName.text = product.name
        binding.tvDescShort.text = product.description
        binding.tvDescFull.text = product.description
        binding.tvTotalprice.text = product.minPrice.toString()
    }

    private fun loadProductDetail(id: Int) {
        productViewModel.getProductsById(id).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.product?.let { product ->
                            setData(product)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        }
    }
}