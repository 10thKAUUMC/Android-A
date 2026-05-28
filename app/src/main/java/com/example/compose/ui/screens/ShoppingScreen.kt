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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.example.compose.R

private val shoppingTabs = listOf("전체", "Tops & T-Shirts", "sale")

@Composable
fun ShoppingScreen(
    products: List<Product>,                  // 상위에서 전달받은 상품 리스트
    onProductLikeClick: (Product) -> Unit     // 상위로 전달할 하트 클릭 이벤트
) {
    val pagerState = rememberPagerState(pageCount = { shoppingTabs.size })
    val coroutineScope = rememberCoroutineScope()

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
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
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
                ProductGridList(products = products, onProductLikeClick = onProductLikeClick)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "${shoppingTabs[page]} 컨텐츠", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun ProductGridList(products: List<Product>, onProductLikeClick: (Product) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(count = products.size, key = { index -> products[index].id }) { index ->
            ProductItem(product = products[index], onLikeClick = { onProductLikeClick(products[index]) })
        }
    }
}

@Composable
fun ProductItem(product: Product, onLikeClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFFF6F6F6), shape = RoundedCornerShape(4.dp))
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onLikeClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.8f), shape = CircleShape)
            ) {
                val heartIcon = if (product.isLiked) R.drawable.ic_heart_on else R.drawable.ic_heart_off
                Icon(
                    painter = painterResource(id = heartIcon),
                    contentDescription = "Wishlist",
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.title, fontSize = 14.sp, color = Color.Black, maxLines = 2)
        Text(text = product.subtitle, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = product.price, fontSize = 14.sp, color = Color.Black)
    }
}