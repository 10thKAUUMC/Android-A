package com.example.week2.domain.repository

import com.example.week2.ProductData
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsStream(): Flow<List<ProductData>>
    suspend fun saveProducts(products: List<ProductData>)
    suspend fun toggleLike(productId: Int)
}
