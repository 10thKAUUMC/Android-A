package com.example.compose.ui.screens

data class Product(
    val id: Int, // 상품 구분을 위한 고유 ID
    val title: String,
    val subtitle: String,
    val price: String,
    val imageResId: Int,
    val isLiked: Boolean = false // 하트 상태
)