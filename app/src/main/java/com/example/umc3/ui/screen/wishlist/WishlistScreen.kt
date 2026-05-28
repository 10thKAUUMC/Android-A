package com.example.umc3.ui.screen.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc3.data.WishlistRepository
import com.example.umc3.ui.components.ProductCard
import com.example.umc3.ui.components.ProductListContentPadding

/**
 * 위시리스트 탭 화면 — 사용자가 찜한 상품 목록 진입점.
 *
 * 데이터 소스: `WishlistRepository.items` (인-메모리, Compose snapshot 기반).
 * Home/Buy의 하트 토글이 그대로 반영되며, 이 화면에서 하트를 다시 누르면 제거된다.
 *
 * 본문은 `LazyVerticalGrid(columns = GridCells.Fixed(2))`로 3주차 GridLayoutManager(2)를 대체.
 */
@Composable
fun WishlistScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    // WishlistRepository.items는 mutableStateListOf → 읽는 순간 Compose가 의존성 추적
    val wished = WishlistRepository.items

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding),
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "위시리스트",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (wished.isEmpty()) {
            // 빈 상태 — Home/Buy에서 하트로 추가하라는 안내. 화면이 휑하지 않도록 가운데 정렬.
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "아직 찜한 상품이 없습니다.\n홈/구매하기에서 하트를 눌러 추가해보세요.",
                    color = Color(0xFF767676),
                    fontSize = 14.sp,
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = ProductListContentPadding,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(
                    items = wished,
                    key = { product -> product.id },
                ) { product ->
                    // 위시리스트 내부 카드도 하트 표시 — 채워진 빨간 하트, 다시 누르면 제거된다.
                    ProductCard(
                        product = product,
                        isWished = true,
                        onHeartClick = { WishlistRepository.toggle(product) },
                        showHeart = true,
                    )
                }
            }
        }
    }
}
