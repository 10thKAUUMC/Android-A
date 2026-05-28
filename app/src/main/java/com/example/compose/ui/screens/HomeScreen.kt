package com.example.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R

// 1. 신발 데이터를 표현하는 클래스 (Data Class)
data class ShoeItem(
    val name: String,
    val price: String,
    val imageResId: Int
)

@Composable
fun HomeScreen() {
    // 2. drawable 리소스로 변경된 신발 데이터 리스트
    val newShoes = listOf(
        ShoeItem(name = "Air Jordan XXXVI", price = "US$185", imageResId = R.drawable.air_jordan),
        ShoeItem(name = "Nike Air Force 1'07", price = "US$115", imageResId = R.drawable.nike_air_force)
    )

    // 전체 화면을 구성하는 LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 상단 타이틀 섹션 (Discover & 날짜)
        item {
            HeaderSection()
        }

        // 중앙 메인 로고 배너 섹션 (loggo.png 적용)
        item {
            MainBannerSection()
        }

        // "What's new / 나이키 최신 상품" 텍스트와 가로 스크롤 신발 리스트 섹션
        item {
            Column {
                Text(
                    text = "What's new",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "나이키 최신 상품",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // items()를 사용해 신발 리스트를 가로로 배치 (LazyRow)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(newShoes) { shoe ->
                        ShoeCard(shoe = shoe)
                    }
                }
            }
        }
    }
}

// --- 분리된 Composable 함수들 ---

@Composable
fun HeaderSection() {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Discover",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "9월 4일 목요일",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun MainBannerSection() {
    // 요청하신대로 가운데 로고 이미지를 drawable/loggo로 변경했습니다.
    Image(
        painter = painterResource(id = R.drawable.loggo),
        contentDescription = "Main Logo Banner",
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ShoeCard(shoe: ShoeItem) {
    // 개별 신발 아이템을 보여주는 카드 UI
    Column(
        modifier = Modifier.width(200.dp) // 각 신발 아이템의 가로 크기 제한
    ) {
        Image(
            painter = painterResource(id = shoe.imageResId),
            contentDescription = shoe.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = shoe.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Text(
            text = shoe.price,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}