package com.example.compose.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.compose.R
import com.example.compose.ui.screens.Product

class MainViewModel : ViewModel() {
    // 상품 리스트 상태를 ViewModel에서 관리하여 화면 회전 시에도 유지
    private val _masterProductList = mutableStateListOf(
        Product(1, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "US$10", R.drawable.nike_everyday_plus),
        Product(2, "Nike Elite Crew", "Basketball Socks", "US$16", R.drawable.nike_elite_crew),
        Product(3, "Nike Air Force 1 '07", "Women's Shoes", "US$115", R.drawable.nike_air_force),
        Product(4, "Jordan Essentials", "Men's Shoes", "US$115", R.drawable.enike)
    )
    val masterProductList: List<Product> get() = _masterProductList

    fun toggleLike(product: Product) {
        val index = _masterProductList.indexOfFirst { it.id == product.id }
        if (index != -1) {
            _masterProductList[index] = _masterProductList[index].copy(isLiked = !_masterProductList[index].isLiked)
        }
    }
}
