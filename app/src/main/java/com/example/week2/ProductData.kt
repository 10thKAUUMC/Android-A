package com.example.week2

// 상품 정보를 담는 데이터 클래스
data class ProductData(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int,       // 여기 이름이 어댑터와 같아야 합니다!
    var isLiked: Boolean = false
)