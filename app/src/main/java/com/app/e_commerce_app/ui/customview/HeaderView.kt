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
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.CustomHeaderViewBinding
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.ui.adapter.CategoryAdapter
import com.app.e_commerce_app.ui.adapter.CategoryButtonAdapter
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CategoryViewModel

class HeaderView: ConstraintLayout {
    private var _binding: CustomHeaderViewBinding? = null
    private val binding get() = _binding!!


    private var categoryList: ArrayList<CategoryModel>? = null
    private val categoryButtonAdapter: CategoryButtonAdapter by lazy {
        CategoryButtonAdapter(context, onCategoryItemButtonClick)
    }
    private val categoryViewModel : CategoryViewModel by lazy {
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[CategoryViewModel::class.java]
    }


    constructor(context: Context) : super(context) {
        inits(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context, attrs
    ) {
        inits(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        inits(context, attrs)
    }

    private fun inits(context: Context, attrs: AttributeSet? = null) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_header_view, this)

        _binding = CustomHeaderViewBinding.inflate(inflater, this, true)

        binding.navigateStore.btnNavigateBack.setOnClickListener {
            Toast.makeText(context, "Back", Toast.LENGTH_SHORT)
                .show()
        }

        binding.rvCate.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCate.adapter = categoryButtonAdapter
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        loadCategory()
    }

    private fun loadCategory() {
        if (categoryList == null) {
            categoryList = ArrayList()

            categoryViewModel.getAllCategories().observe(findViewTreeLifecycleOwner()!!) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                categoryList = data.categories
                                categoryButtonAdapter.setCategories(categoryList as ArrayList<CategoryModel>)
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

    private val onCategoryItemButtonClick: (CategoryRadioButton) -> Unit = {
    }
}