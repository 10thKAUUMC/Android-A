package com.example.umc3.data

import androidx.annotation.DrawableRes
import com.example.umc3.R
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val badge: String?,
    val title: String,
    val subtitle: String,
    val price: String,
    @DrawableRes val imageRes: Int,
)

/**
 * 홈 "나이키 최신 상품" 가로 스크롤용 더미 데이터.
 * drawable에 실제 존재하는 두 상품(Air Jordan / Nike Air Force)만 남긴다.
 */
val homeLatestProducts: List<Product> = listOf(
    Product(
        id = 1,
        badge = null,
        title = "Air Jordan XXXVI",
        subtitle = "Men's Basketball Shoes",
        price = "US$185",
        imageRes = R.drawable.air_jordan,
    ),
    Product(
        id = 2,
        badge = null,
        title = "Nike Air Force 1'07",
        subtitle = "Men's Shoes",
        price = "US$115",
        imageRes = R.drawable.nike_air_force,
    ),
)

/**
 * 구매하기 탭 2열 격자용 더미 데이터.
 * drawable에 실제 존재하는 두 상품(Air Jordan / Nike Air Force)만 남긴다.
 */
val buyProducts: List<Product> = listOf(
    Product(
        id = 101,
        badge = null,
        title = "Air Jordan XXXVI",
        subtitle = "Men's Basketball Shoes",
        price = "US$185",
        imageRes = R.drawable.air_jordan,
    ),
    Product(
        id = 102,
        badge = "BestSeller",
        title = "Nike Air Force 1",
        subtitle = "Women's Shoes",
        price = "US$115",
        imageRes = R.drawable.nike_air_force,
    ),
)

