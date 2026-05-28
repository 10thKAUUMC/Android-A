package com.example.umc3.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.umc3.ui.components.NikeBottomBar
import com.example.umc3.ui.navigation.AppDestination
import com.example.umc3.ui.screen.buy.BuyScreen
import com.example.umc3.ui.screen.cart.CartScreen
import com.example.umc3.ui.screen.home.HomeScreen
import com.example.umc3.ui.screen.profile.ProfileScreen
import com.example.umc3.ui.screen.wishlist.WishlistScreen

/**
 * 앱 최상위 컴포저블 — 라우팅과 화면 조립의 단일 진입점.
 *
 * 책임 4가지:
 *  1. NavController 보유 — rememberNavController()로 라이프사이클 안전한 controller 단일 생성.
 *  2. Scaffold 골격 — bottomBar에 NikeBottomBar, content에 NavHost 배치.
 *  3. NavHost 구성 — 5개 destination을 type-safe API(composable<T>)로 등록.
 *  4. 이벤트 라우팅 — CartScreen.onOrderClick 같은 자식 이벤트를 navigate로 변환.
 */
@Composable
fun NikeApp() {
    val navController = rememberNavController()

    // 현재 백스택 최상단을 observation → BottomBar 하이라이트 결정
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: AppDestination? = currentBackStackEntry?.destination?.toAppDestination()

    /**
     * BottomBar 탭 클릭과 CartScreen "주문하기"가 공유하는 라우팅 람다.
     *
     * - popUpTo(startDestination, saveState = true)
     *     백스택을 시작점까지 정리 → Home에서 뒤로가기 시 앱 종료. saveState=true로 떠나는 화면 상태 보관.
     * - launchSingleTop = true
     *     같은 destination 중복 push 방지.
     * - restoreState = true
     *     이전 saveState로 저장된 상태 복원 → 탭 전환 시 스크롤 위치 유지.
     */
    val navigateToTab: (AppDestination) -> Unit = { destination ->
        navController.navigate(destination) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NikeBottomBar(
                currentDestination = currentDestination,
                onItemSelected = navigateToTab,
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home,
        ) {
            composable<AppDestination.Home> {
                HomeScreen(contentPadding = innerPadding)
            }
            composable<AppDestination.Buy> {
                BuyScreen(contentPadding = innerPadding)
            }
            composable<AppDestination.Wishlist> {
                WishlistScreen(contentPadding = innerPadding)
            }
            composable<AppDestination.Cart> {
                CartScreen(
                    contentPadding = innerPadding,
                    // ★ 핵심: 주문하기 → 구매하기 탭 이동. BottomBar 하이라이트도 자동 반영.
                    onOrderClick = { navigateToTab(AppDestination.Buy) },
                )
            }
            composable<AppDestination.Profile> {
                ProfileScreen(contentPadding = innerPadding)
            }
        }
    }
}

/**
 * NavDestination → AppDestination 매핑 헬퍼.
 * hasRoute<T>는 nav-compose 2.8+ type-safe 매칭 API. 문자열 비교 없이 reified 제네릭으로 식별.
 */
private fun NavDestination.toAppDestination(): AppDestination? = when {
    hasRoute<AppDestination.Home>() -> AppDestination.Home
    hasRoute<AppDestination.Buy>() -> AppDestination.Buy
    hasRoute<AppDestination.Wishlist>() -> AppDestination.Wishlist
    hasRoute<AppDestination.Cart>() -> AppDestination.Cart
    hasRoute<AppDestination.Profile>() -> AppDestination.Profile
    else -> null
}
