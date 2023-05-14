package com.app.e_commerce_app.utils

import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRadioButton
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.model.variation.VariationOptionModel

typealias OnCategoryItemButtonClick = (CategoryRadioButton) -> Unit
typealias OnCategoryIconButtonClick = (CategoryModel) -> Unit
typealias OnProductItemClick = (ProductModel) -> Unit
typealias OnVariationOptionClick = (VariationOptionModel) -> Unit
