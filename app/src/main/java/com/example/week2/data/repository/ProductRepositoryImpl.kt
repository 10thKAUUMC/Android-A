package com.example.week2.data.repository

import com.example.week2.ProductData
import com.example.week2.data.local.ProductDataStore
import com.example.week2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataStore: ProductDataStore
) : ProductRepository {

    override fun getProductsStream(): Flow<List<ProductData>> = productDataStore.productsFlow

    override suspend fun saveProducts(products: List<ProductData>) {
        productDataStore.saveProducts(products)
    }

    override suspend fun toggleLike(productId: Int) {
        val current = productDataStore.productsFlow.first()
        val updated = current.map {
            if (it.id == productId) it.copy(isLiked = !it.isLiked) else it
        }
        productDataStore.saveProducts(updated)
    }
}
