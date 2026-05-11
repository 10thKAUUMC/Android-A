package com.example.week2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week2.ProductData
import com.example.week2.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val initialProducts = listOf(
        ProductData(1, "Air Jordan XXXVI", "US\$185", "air_jordan", false),
        ProductData(2, "Nike Air Force 1'07", "US\$115", "nike_air_force", false)
    )

    init {
        viewModelScope.launch {
            productRepository.getProductsStream().collect { products ->
                if (products.isEmpty() || products.any { it.imageResName.isEmpty() }) {
                    productRepository.saveProducts(initialProducts)
                } else {
                    _uiState.update { it.copy(products = products) }
                }
            }
        }
    }

    fun toggleLike(product: ProductData) {
        viewModelScope.launch {
            productRepository.toggleLike(product.id)
        }
    }
}

data class HomeUiState(
    val products: List<ProductData> = emptyList()
)
