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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.ui.viewmodel.MainViewModel

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
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val masterProductList = viewModel.masterProductList
    val onToggleLike: (Product) -> Unit = { viewModel.toggleLike(it) }

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
