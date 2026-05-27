package com.example.compose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WishlistScreen(
    likedProducts: List<Product>,             // 상위에서 'isLiked == true'인 것만 걸러서 받아옴
    onProductLikeClick: (Product) -> Unit     // 위시리스트에서 하트 해제 시 호출할 이벤트
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "위시리스트",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 8.dp)
        )

        if (likedProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "위시리스트가 비어 있습니다.", color = Color.Gray, fontSize = 16.sp)
            }
        } else {
            // ShoppingScreen에 있는 GridList 구조와 동일하게 표시
            ProductGridList(products = likedProducts, onProductLikeClick = onProductLikeClick)
        }
    }
}