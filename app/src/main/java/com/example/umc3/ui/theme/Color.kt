package com.example.umc3.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

/**
 * Nike 앱 공용 색상 팔레트.
 *
 * 여러 화면(Home/Buy/Cart/Wishlist/Profile/BottomBar/ProductCard)에 흩어져 있던
 * 같은 hex 리터럴을 한 곳으로 모아 단일 진실 공급원(SSOT)으로 만든다.
 * 색을 바꿔야 할 때 이 파일만 고치면 전 화면에 반영된다.
 */
val NikeSubText = Color(0xFF767676)      // 보조 텍스트(회색)
val NikeDivider = Color(0xFFEEEEEE)      // 구분선
val NikePlaceholder = Color(0xFFF5F5F5)  // 카드/화면 플레이스홀더 배경
val NikeRed = Color(0xFFE81E26)          // 찜(하트) 활성·에러 강조
val NikeBadgeOrange = Color(0xFFFA5400)  // BestSeller 배지
val NikeAvatarGray = Color(0xFFD9D9D9)   // 프로필 아바타 플레이스홀더
val NikeBorder = Color(0xFFE5E5E5)       // 버튼 외곽선