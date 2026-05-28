package com.example.umc3.ui.screen.buy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc3.data.WishlistRepository
import com.example.umc3.data.buyProducts
import com.example.umc3.ui.components.ProductCard
import com.example.umc3.ui.components.ProductListContentPadding

/**
 * 구매하기 탭 화면 — 카테고리별 상품 목록 진입점.
 *
 * ## 상태 관리
 * - 선택된 탭 인덱스는 현재 화면 내부에서만 의미 있는 UI 상태라 remember로 로컬 보관.
 * - 추후 데이터 페칭이 붙으면 selectedIndex를 ViewModel(StateFlow)로 hoisting하면 됨.
 *
 * ## 본문 — 2열 격자
 * 3주차 ShoppingFragment의 `GridLayoutManager(context, 2)` + `HomeAdapter`를
 * `LazyVerticalGrid(columns = GridCells.Fixed(2))`로 교체. 카테고리 필터링은 시니어 미션
 * 범위라 이번 주차에서는 전체 상품을 그대로 노출한다.
 */
@Composable
fun BuyScreen(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
) {
    val tabs = listOf("전체", "Tops & T-Shirts", "Shoes")
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding),
    ) {
        // 가로 탭 줄 — Arrangement.spacedBy로 자식 간 간격 20dp 보장
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEachIndexed { index, label ->
                BuyTopTab(
                    label = label,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFEEEEEE),
        )

        // 2열 격자 — RecyclerView + GridLayoutManager(2) 자리에 LazyVerticalGrid를 끼움.
        // 자체 스크롤을 가지므로 부모 Column에 verticalScroll을 두지 않아야 한다(중첩 스크롤 충돌).
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = ProductListContentPadding,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                items = buyProducts,
                key = { product -> product.id },
            ) { product ->
                ProductCard(
                    product = product,
                    isWished = WishlistRepository.isWished(product),
                    onHeartClick = { WishlistRepository.toggle(product) },
                    showHeart = true,
                )
            }
        }
    }
}

/**
 * BuyScreen 상단 탭 1개. 라벨/선택여부/클릭콜백을 외부에서 주입받는 State Hoisting 형태.
 */
@Composable
private fun BuyTopTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSelected) Color.Black else Color(0xFF767676)
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    // 미선택일 때 Transparent로 라인 자리만 차지 → 텍스트가 위아래로 튀지 않게.
    val indicatorColor = if (isSelected) Color.Black else Color.Transparent

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            // Column 너비를 자식 Text의 최대 본문 너비로 강제 → 인디케이터 fillMaxWidth가 텍스트와 같은 폭이 됨
            .width(IntrinsicSize.Max)
            .height(48.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = fontWeight,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(indicatorColor),
        )
    }
}
