package com.example.umc3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc3.ui.navigation.AppDestination
import com.example.umc3.ui.navigation.BottomNavItem
import com.example.umc3.ui.navigation.bottomNavItems
import com.example.umc3.ui.theme.NikeSubText

/**
 * Nike 앱 하단 커스텀 BottomBar.
 *
 * ## 책임
 * - bottomNavItems(5개 탭)을 가로로 균등 배치하고, 현재 선택된 탭만 검정/Bold로 강조한다.
 * - 탭이 눌리면 어떤 destination이 선택됐는지 상위로 알린다 (네비게이션은 직접 하지 않음).
 *
 * ## State Hoisting
 * - 컴포저블 내부에 selectedIndex 같은 자체 상태를 두지 않는다.
 * - currentDestination은 외부에서 주입, 탭 클릭은 onItemSelected 콜백으로 외부에 위임.
 * → NavController가 단일 진실 공급원(SSOT)이 되어 회전/재구성에도 일관성 유지.
 */
@Composable
fun NikeBottomBar(
    currentDestination: AppDestination?,
    onItemSelected: (AppDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
            .height(60.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        bottomNavItems.forEach { item ->
            BottomBarItem(
                item = item,
                // data object라 reference equality(==)로 안전하게 식별 가능
                isSelected = item.destination == currentDestination,
                onClick = { onItemSelected(item.destination) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

/**
 * BottomBar의 단일 탭(아이콘 + 라벨) 컴포저블.
 * NikeBottomBar 내부에서만 사용되어 private으로 격리.
 */
@Composable
private fun BottomBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isSelected) Color.Black else NikeSubText
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    // ripple/indication 제거. interactionSource는 remember로 감싸 누수 방지.
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = item.label,
            // Vector Drawable의 strokeColor를 tint로 덮어써서 선택 상태를 표현
            tint = contentColor,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = item.label,
            color = contentColor,
            fontSize = 10.sp,
            fontWeight = fontWeight,
        )
    }
}
