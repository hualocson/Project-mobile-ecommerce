package com.app.e_commerce_app.utils

import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.product.ProductModel

typealias OnCategoryItemButtonClick = (CategoryRadioButton) -> Unit
typealias OnCategoryIconButtonClick = (CategoryModel) -> Unit
typealias OnProductItemClick = (ProductModel) -> Unit
