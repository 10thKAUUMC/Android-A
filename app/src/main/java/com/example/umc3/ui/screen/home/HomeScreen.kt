package com.example.umc3.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc3.R

/**
 * 홈 탭 화면 — "Discover" 피드 진입점.
 *
 * 시안 구성: 16dp 여백 → Discover(32sp Bold) → 4dp → 날짜(13sp 회색)
 * → 20dp → 메인 이미지(라운드 4dp) → 24dp.
 * 콘텐츠가 길어질 수 있어 verticalScroll로 감쌌다.
 */
@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            // Scaffold inner padding을 가장 바깥에서 소비 — BottomBar 영역 침범 방지
            .padding(contentPadding)
            // 스크롤은 contentPadding 이후에 적용 — inset 영역은 고정시키고 콘텐츠만 스크롤
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Discover",
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            // 시안 그대로 하드코딩. 실제는 LocalDate.now() 포매팅으로 대체 예정.
            text = "9월 4일 목요일",
            color = Color(0xFF767676),
            fontSize = 13.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.image_discover),
            contentDescription = "Discover banner",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
