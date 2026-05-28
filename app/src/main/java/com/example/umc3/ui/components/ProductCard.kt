package com.example.umc3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc3.data.Product

/**
 * 상품 카드 — Home/Buy/Wishlist 세 화면이 공유하는 단일 아이템 컴포저블.
 *
 * 3주차 `item_home.xml` + `HomeViewHolder.bind()`가 표현하던 요소
 * (이미지 / 우상단 하트 / "BestSeller" 배지 / 타이틀 / 서브타이틀 / 가격)를 그대로 재현한다.
 *
 * @param product 표시할 상품 데이터.
 * @param isWished 위시리스트 등록 여부. true면 빨간 채워진 하트, false면 검은 빈 하트.
 * @param onHeartClick 하트 아이콘 클릭 콜백. 상위에서 WishlistRepository.toggle() 호출.
 * @param modifier 외부 레이아웃 제약(가로 카드 width 지정 등)을 주입받기 위한 modifier.
 * @param showHeart 우상단 하트 표시 여부. 일반적으로는 true, 하트가 의미 없는 화면에서만 false.
 */
@Composable
fun ProductCard(
    product: Product,
    isWished: Boolean,
    onHeartClick: () -> Unit,
    modifier: Modifier = Modifier,
    showHeart: Boolean = true,
    showSubtitle: Boolean = true,
) {
    Column(modifier = modifier) {
        // 이미지 영역 — 1:1 정사각, 우상단에 하트 오버레이
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5)),
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
            )
            if (showHeart) {
                // clickable을 padding 앞에 두어 패딩 영역까지 터치 타겟에 포함 → 작은 아이콘도 누르기 편함
                Icon(
                    imageVector = if (isWished) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isWished) "위시리스트에서 제거" else "위시리스트에 추가",
                    tint = if (isWished) Color(0xFFE81E26) else Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable(onClick = onHeartClick)
                        .padding(8.dp)
                        .size(20.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 배지(있을 때만) — 3주차 item_home.xml의 itemState 영역
        if (!product.badge.isNullOrEmpty()) {
            Text(
                text = product.badge,
                color = Color(0xFFFA5400),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp),
            )
        }

        Text(
            text = product.title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        if (showSubtitle) {
            Text(
                text = product.subtitle,
                color = Color(0xFF767676),
                fontSize = 13.sp,
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = product.price,
            color = Color.Black,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

/**
 * 가로 LazyRow에서 카드 너비를 통일하기 위한 고정 너비 래퍼.
 *
 * LazyRow는 children 너비를 자동으로 잡아주지 않기 때문에 별도 width 제약이 필요하다.
 * 3주차 RecyclerView + 가로 LinearLayoutManager 구성에서 item이 `wrap_content` 너비를
 * 가지던 패턴을, 시각적으로 안정되도록 160dp로 고정해서 재현.
 */
@Composable
fun HorizontalProductCard(
    product: Product,
    modifier: Modifier = Modifier,
) {
    ProductCard(
        product = product,
        isWished = false,
        onHeartClick = {},
        modifier = modifier.width(314.dp),
        showHeart = false,
        showSubtitle = false,
    )
}

/** Lazy 그리드/로우의 콘텐츠 외곽 패딩 기본값 — 가로 16dp, 세로 12dp. 화면 가독성용 공통 상수. */
val ProductListContentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
