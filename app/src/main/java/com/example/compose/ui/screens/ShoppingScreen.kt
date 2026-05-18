package com.example.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.example.compose.R // 본인의 패키지명에 맞게 R 클래스를 임포트하세요.

private val shoppingTabs = listOf("전체", "Tops & T-Shirts", "sale")

// 이미지 리소스 ID 필드를 추가한 상품 데이터 클래스
data class Product(
    val title: String,
    val subtitle: String,
    val price: String,
    val imageResId: Int
)

@Composable
fun ShoppingScreen() {
    val pagerState = rememberPagerState(pageCount = { shoppingTabs.size })
    val coroutineScope = rememberCoroutineScope()

    // 알려주신 에셋 매핑 데이터 리스트
    val dummyProducts = remember {
        listOf(
            Product(
                title = "Nike Everyday Plus Cushioned",
                subtitle = "Training Ankle Socks (6 Pairs)",
                price = "US$10",
                imageResId = R.drawable.nike_everyday_plus
            ),
            Product(
                title = "Nike Elite Crew",
                subtitle = "Basketball Socks",
                price = "US$16",
                imageResId = R.drawable.nike_elite_crew
            ),
            Product(
                title = "Nike Air Force 1 '07",
                subtitle = "Women's Shoes",
                price = "US$115",
                imageResId = R.drawable.nike_air_force
            ),
            Product(
                title = "Jordan ENike Air Force 1 '07ssentials",
                subtitle = "Men's Shoes",
                price = "US$115",
                imageResId = R.drawable.enike
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            contentColor = Color.Black,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Black
                )
            },
            divider = {}
        ) {
            shoppingTabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 0) {
                ProductGridList(products = dummyProducts)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${shoppingTabs[page]} 컨텐츠", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun ProductGridList(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(products.size) { index ->
            ProductItem(product = products[index])
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    // 하트 버튼 상태 관리 (초기값: false = 하얀색 하트)
    // rememberSaveable을 사용하면 화면을 돌려도 상태가 유지됩니다.
    var isLiked by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // 이미지와 하트 버튼을 겹치기 위해 Box 사용
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFFF6F6F6), shape = RoundedCornerShape(4.dp))
        ) {
            // 1. 실제 상품 이미지 적용
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // 박스에 가득 차게 크롭 처리
            )

            // 2. 우측 상단 하트 버튼 영역
            IconButton(
                onClick = { isLiked = !isLiked }, // 누를 때마다 상태 반전
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.8f), shape = CircleShape) // 버튼 뒤 배경 원형 처리
            ) {
                // 오타 반영된 파일명 그대로 적용 완료
                val heartIcon = if (isLiked) {
                    R.drawable.ic_heart_on  // 빨간색 하트
                } else {
                    R.drawable.ic_heart_off // 하얀색 하트
                }

                Icon(
                    painter = painterResource(id = heartIcon),
                    contentDescription = "Wishlist",
                    tint = Color.Unspecified // 아이콘 자체의 원본 색상(빨간색/하얀색)을 그대로 유지
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 상품 정보 영역
        Text(
            text = product.title,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 2
        )
        Text(
            text = product.subtitle,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.price,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShoppingScreenPreview() {
    ShoppingScreen()
}