package com.example.week2

// 상품 정보를 담는 데이터 클래스
data class ProductData(
    val name: String,     // 상품 이름
    val price: String,    // 상품 가격
    val imageRes: Int     // 상품 이미지 (R.drawable.파일명)
)