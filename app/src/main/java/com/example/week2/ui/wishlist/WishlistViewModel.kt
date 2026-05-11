package com.example.week2.ui.wishlist

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
class WishlistViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.getProductsStream().collect { products ->
                _uiState.update { it.copy(likedProducts = products.filter { p -> p.isLiked }) }
            }
        }
    }

    fun toggleLike(product: ProductData) {
        viewModelScope.launch {
            productRepository.toggleLike(product.id)
        }
    }
}

data class WishlistUiState(
    val likedProducts: List<ProductData> = emptyList()
)
