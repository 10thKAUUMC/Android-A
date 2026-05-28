package com.example.compose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import com.example.compose.ui.screens.*
import androidx.compose.ui.unit.dp

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val iconRes: Int
) {
    object Home     : BottomNavItem("home",     "홈",      R.drawable.ic_menu_home2)
    object Shopping : BottomNavItem("shopping", "구매하기", R.drawable.ic_menu_search)
    object Wishlist : BottomNavItem("wishlist", "위시리스트", R.drawable.ic_menu_wishlist)
    object Bag      : BottomNavItem("bag",      "장바구니", R.drawable.ic_menu_bag)
    object Profile  : BottomNavItem("profile",  "프로필",   R.drawable.ic_menu_user)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Shopping,
    BottomNavItem.Wishlist,
    BottomNavItem.Bag,
    BottomNavItem.Profile
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // ★ [추가] 두 화면이 공유할 마스터 상품 리스트 상태 관리 (고유 ID 추가)
    val masterProductList = remember {
        mutableStateListOf(
            Product(1, "Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)", "US$10", R.drawable.nike_everyday_plus),
            Product(2, "Nike Elite Crew", "Basketball Socks", "US$16", R.drawable.nike_elite_crew),
            Product(3, "Nike Air Force 1 '07", "Women's Shoes", "US$115", R.drawable.nike_air_force),
            Product(4, "Jordan ENike Air Force 1 '07ssentials", "Men's Shoes", "US$115", R.drawable.enike)
        )
    }

    // ★ [추가] 하트 클릭 시 리스트 내 아이템 상태를 반전시키는 토글 함수
    val onToggleLike: (Product) -> Unit = { targetedProduct ->
        val index = masterProductList.indexOfFirst { it.id == targetedProduct.id }
        if (index != -1) {
            masterProductList[index] = masterProductList[index].copy(isLiked = !masterProductList[index].isLiked)
        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                bottomNavItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route)     { HomeScreen() }

            // ★ [변경] ShoppingScreen에 데이터 리스트와 이벤트 핸들러 주입
            composable(BottomNavItem.Shopping.route) {
                ShoppingScreen(
                    products = masterProductList,
                    onProductLikeClick = onToggleLike
                )
            }

            // ★ [변경] WishlistScreen에 좋아요(isLiked)된 상품만 필터링하여 주입
            composable(BottomNavItem.Wishlist.route) {
                val likedProducts = masterProductList.filter { it.isLiked }
                WishlistScreen(
                    likedProducts = likedProducts,
                    onProductLikeClick = onToggleLike
                )
            }

            composable(BottomNavItem.Bag.route) { BagScreen(navController = navController) }
            composable(BottomNavItem.Profile.route)  { ProfileScreen() }
        }
    }
}