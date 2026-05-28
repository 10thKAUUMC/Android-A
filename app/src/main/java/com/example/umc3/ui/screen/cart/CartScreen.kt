package com.example.umc3.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc3.R

/**
 * 장바구니 탭 화면 — 빈 상태 안내 + "주문하기" CTA.
 *
 * ## State Hoisting — Events Flow Up
 * 이 화면은 navigate를 직접 수행하지 않고, onOrderClick 이벤트만 위로 올린다.
 * NavController는 부모(NikeApp)가 소유하고 있으므로, "주문하기 → 구매하기 탭 이동" 같은 라우팅 결정은 부모가 책임진다.
 */
@Composable
fun CartScreen(
    onOrderClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding),
    ) {
        // 중앙 안내 영역 — 하단 버튼과 시각적으로 겹치지 않도록 80dp 띄움
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bag_empty),
                contentDescription = "빈 장바구니",
                tint = Color.Black,
                modifier = Modifier.size(60.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "장바구니가 비어 있습니다.",
                color = Color(0xFF767676),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "제품을 추가하면 여기에 표시됩니다.",
                color = Color(0xFF767676),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
        }

        // 하단 "주문하기" 버튼 — Box align(BottomCenter)로 화면 바닥에 고정
        Button(
            onClick = onOrderClick,
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            // padding(16.dp) → height(53.dp) 순서: 외부 여백 먼저 확보 후 버튼 자체 높이 53dp로 고정
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(53.dp),
        ) {
            Text(
                text = "주문하기",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
