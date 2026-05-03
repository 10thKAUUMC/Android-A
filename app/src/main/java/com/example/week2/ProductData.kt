package com.example.week2

// 상품 정보를 담는 데이터 클래스
data class ProductData(
    val id: Int,
    val name: String,
    val price: String,
    val imageResName: String,  // drawable 파일명 (예: "air_jordan")
    var isLiked: Boolean = false
)