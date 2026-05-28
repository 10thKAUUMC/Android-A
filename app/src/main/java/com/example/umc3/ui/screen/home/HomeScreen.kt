package com.example.umc3.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.umc3.data.homeLatestProducts
import com.example.umc3.ui.components.HorizontalProductCard

/**
 * 홈 탭 화면 — "Discover" 피드 진입점.
 *
 * 시안 구성: 16dp 여백 → Discover(32sp Bold) → 4dp → 날짜(13sp 회색)
 * → 20dp → 메인 이미지(라운드 4dp) → 24dp → "나이키 최신 상품" 섹션(가로 LazyRow).
 *
 * 콘텐츠가 길어질 수 있어 verticalScroll로 감쌌다. LazyRow는 verticalScroll 안에 있어도
 * 자기 자식만 가로 방향으로 가상화하므로 부모 세로 스크롤과 충돌하지 않는다.
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
            .verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
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
            Text(
                text = "나이키 최신 상품",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // 가로 스크롤 목록 — RecyclerView + 가로 LinearLayoutManager를 LazyRow로 대체.
        // contentPadding을 LazyRow 자체에 부여하면 첫/마지막 카드만 좌우 16dp 여백이 잡혀,
        // 스크롤 끝 위치가 자연스럽다. (Column 패딩으로 처리하면 가상화된 자식이 잘림.)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 42.dp, end = 42.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(
                items = homeLatestProducts,
                key = { product -> product.id },
            ) { product ->
                HorizontalProductCard(product = product)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
