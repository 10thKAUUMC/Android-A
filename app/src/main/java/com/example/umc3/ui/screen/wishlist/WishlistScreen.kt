package com.example.umc3.ui.screen.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 위시리스트 탭 화면 — 사용자가 찜한 상품 목록 진입점.
 *
 * 이번 주차는 라우팅 골격 확인이 목적이라 타이틀만 노출하고 콘텐츠는 비워둔다.
 */
@Composable
fun WishlistScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding)
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "위시리스트",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
