package com.example.umc3.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * 프로필 탭 화면 — 내 계정 / 주문 / 설정 진입점.
 * 이번 주차는 라우팅 확인이 목적이라 빈 Box + 흰 배경만 유지.
 */
@Composable
fun ProfileScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding),
    )
}
