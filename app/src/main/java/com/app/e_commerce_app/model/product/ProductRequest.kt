package com.app.e_commerce_app.model.product

data class ProductRequest(
    private val categoryId: Int,
    private var name: String,
    private var description: String,
    private var imageUrl: String
) {
    val isUpdateDataValid : Boolean
    get()  {
        name = name.trim()
        description = description.trim()
        imageUrl = imageUrl.trim()
        return name.isNotBlank() && description.isNotBlank()
    }
    val isCreateDataValid : Boolean
        get()  {
            name = name.trim()
            description = description.trim()
            imageUrl = imageUrl.trim()
            return name.isNotBlank() && description.isNotBlank() && categoryId != 0
        }

    companion object {
        fun getUpdateData(name: String, desc: String, img: String) : ProductRequest {
            return ProductRequest(0, name, desc, img)
        }
        fun getCreateDate(categoryId: Int ,name: String, desc: String, img: String) : ProductRequest {
            return ProductRequest(categoryId, name, desc, img)
        }
    }
}