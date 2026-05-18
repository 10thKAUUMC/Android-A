package com.example.umc3.ui.navigation

import androidx.annotation.DrawableRes
import com.example.umc3.R

/**
 * BottomNavigationBar의 각 탭을 표현하는 데이터 모델.
 *
 * destination(어디로 갈지) / iconRes(어떤 아이콘) / label(무엇을 보여줄지)을
 * 하나로 묶어 UI 코드(NavigationBarItem)와 Navigation 로직을 분리한다.
 */
data class BottomNavItem(
    val destination: AppDestination,
    @DrawableRes val iconRes: Int,
    val label: String,
)

/**
 * 화면 하단 5개 탭 정의.
 *
 * 리스트 순서가 곧 BottomBar 표시 순서이므로
 * 시안(Home → Buy → Wishlist → Cart → Profile)에 맞게 고정한다.
 */
val bottomNavItems: List<BottomNavItem> = listOf(
    BottomNavItem(AppDestination.Home, R.drawable.nav_home, "홈"),
    BottomNavItem(AppDestination.Buy, R.drawable.nav_buy, "구매하기"),
    BottomNavItem(AppDestination.Wishlist, R.drawable.nav_wishlist, "위시리스트"),
    BottomNavItem(AppDestination.Cart, R.drawable.nav_bag, "장바구니"),
    BottomNavItem(AppDestination.Profile, R.drawable.nav_profile, "프로필"),
)
