package com.example.umc3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.umc3.data.WishlistRepository
import com.example.umc3.ui.NikeApp
import com.example.umc3.ui.theme.UMC3Theme

/**
 * 앱 진입점 Activity.
 *
 * 책임:
 *  1. Edge-to-edge 모드 활성화 (status bar / navigation bar 영역까지 콘텐츠 영역 확장)
 *  2. Compose 진입 — UMC3Theme로 감싼 뒤 NikeApp 한 줄 호출
 *
 * 라우팅/Scaffold/화면 구성은 NikeApp이 모두 책임지므로 여기는 비워둔다.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // NikeBottomBar에서 navigationBarsPadding()으로 inset을 직접 처리하므로 충돌 없음.
        WishlistRepository.init(this)
        enableEdgeToEdge()
        setContent {
            UMC3Theme {
                NikeApp()
            }
        }
    }
}
