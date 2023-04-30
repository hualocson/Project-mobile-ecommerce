package com.app.e_commerce_app.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.databinding.CustomCategoryRadioButtonListBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.utils.OnCategoryItemButtonClick
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel

class CategoryRadioList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomCategoryRadioButtonListBinding? = null

    private val binding get() = _binding!!


    private var categoryList: ArrayList<CategoryModel>? = null
    private var categoryButtonAdapter: CategoryButtonAdapter? = null
    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[CategoryViewModel::class.java]
    }

    init {
        _binding =
            CustomCategoryRadioButtonListBinding.inflate(LayoutInflater.from(context), this, true)

        binding.rvCategoryList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null

        categoryList!!.clear()
        categoryList = null
        categoryButtonAdapter = null
    }

    fun setAdapter(
        onCategoryItemButtonClick: OnCategoryItemButtonClick,
        adapter: CategoryButtonAdapter? = null,
        ) {
        categoryButtonAdapter = adapter ?: CategoryButtonAdapter(context, onCategoryItemButtonClick)
        binding.rvCategoryList.adapter = categoryButtonAdapter
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.rvCategoryList.layoutManager = layoutManager
    }

    fun loadCategory(id: Int = 0) {
        if (categoryList == null) {
            categoryList = ArrayList()

            categoryViewModel.getAllCategories().observe(findViewTreeLifecycleOwner()!!) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                categoryList = data.categories
                                categoryButtonAdapter!!.setCategories(
                                    categoryList as ArrayList<CategoryModel>,
                                    id
                                )
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
//                            Toast.makeText(requireContext(), "Category Loading", Toast.LENGTH_SHORT)
//                                .show()
                        }
                    }
                }
            }
        }
    }
}

