package com.example.umc3.ui.navigation

import kotlinx.serialization.Serializable

/**
 * 앱의 모든 최상위 화면 경로를 한 곳에서 관리하는 sealed interface.
 *
 * - sealed로 묶으면 when 분기에서 모든 destination 처리를 컴파일러가 강제해주고,
 * - Nav Compose 2.8+의 type-safe API를 쓰기 위해 각 destination은 @Serializable data object로 선언한다.
 */
sealed interface AppDestination {

    /** 홈 탭 — 메인 피드/추천 상품을 보여주는 시작 화면 */
    @Serializable
    data object Home : AppDestination

    /** 구매하기 탭 — 상품 카탈로그/검색 진입점 */
    @Serializable
    data object Buy : AppDestination

    /** 위시리스트 탭 — 사용자가 찜한 상품 목록 */
    @Serializable
    data object Wishlist : AppDestination

    /** 장바구니 탭 — 결제 직전 담아둔 상품 목록 */
    @Serializable
    data object Cart : AppDestination

    /** 프로필 탭 — 내 계정/주문/설정 진입점 */
    @Serializable
    data object Profile : AppDestination
}
