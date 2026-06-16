package com.example.compose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

data class FollowingUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String
)

@Composable
fun ProfileScreen() {
    var nickname by remember { mutableStateOf("로드 중...") }
    var avatarUrl by remember { mutableStateOf<String?>(null) }
    var followingUsers by remember { mutableStateOf<List<FollowingUser>>(emptyList()) }

    // API 키 노출 주의 (실제 운영 시 BuildConfig 등으로 관리 권장)
    val apiKey = "reqres_468c6f71952646528b8234815e9906b4"

    suspend fun fetchUserImproved(userId: Int): Result<FollowingUser> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://reqres.in/api/users/$userId")
                val connection = (url.openConnection() as java.net.HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 10000
                    readTimeout = 10000
                    setRequestProperty("User-Agent", "Mozilla/5.0")
                    setRequestProperty("Accept", "application/json")
                    setRequestProperty("x-api-key", apiKey)
                }

                if (connection.responseCode == java.net.HttpURLConnection.HTTP_OK) {
                    val json = JSONObject(connection.inputStream.bufferedReader().use { it.readText() })
                    val data = json.getJSONObject("data")
                    Result.success(FollowingUser(
                        id = data.getInt("id"),
                        firstName = data.getString("first_name"),
                        lastName = data.getString("last_name"),
                        avatarUrl = data.getString("avatar")
                    ))
                } else {
                    Result.failure(Exception("HTTP Error: ${connection.responseCode}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    LaunchedEffect(Unit) {
        val meResult = fetchUserImproved(1)
        meResult.onSuccess { me ->
            nickname = "${me.firstName} ${me.lastName}"
            avatarUrl = me.avatarUrl
        }.onFailure {
            nickname = "로드 실패"
        }

        val users = (2..7).map { id ->
            async { fetchUserImproved(id) }
        }.awaitAll().mapNotNull { it.getOrNull() }

        followingUsers = users
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. 상단 프로필 이미지 및 닉네임 섹션
            item {
                Spacer(modifier = Modifier.height(40.dp))

                if (avatarUrl != null) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = "프로필 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD9D9D9))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = nickname,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { /* 프로필 수정 동작 */ },
                    shape = RoundedCornerShape(24.dp),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                        width = 1.dp,
                        brush = androidx.compose.ui.graphics.SolidColor(Color.LightGray.copy(alpha = 0.5f))
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(44.dp)
                ) {
                    Text(text = "프로필 수정", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            // 2. 4분할 메뉴 섹션
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MenuIconItem(iconResId = R.drawable.ic_order, label = "주문")
                    MenuDivider()
                    MenuIconItem(iconResId = R.drawable.ic_passcard, label = "패스")
                    MenuDivider()
                    MenuIconItem(iconResId = R.drawable.ic_calander, label = "이벤트")
                    MenuDivider()
                    MenuIconItem(iconResId = R.drawable.ic_gear, label = "설정")
                }

                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(0xFFF5F5F5))
                )
            }

            // 3. 멤버 혜택 배너 섹션
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "나이키 멤버 혜택",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "0개 사용 가능",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "자세히 보기",
                        tint = Color.Black
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(0xFFF5F5F5))
                )
            }

            // 4. 팔로잉 섹션
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "팔로잉 (${followingUsers.size})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "편집",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (followingUsers.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.LightGray)
                        }
                    } else {
                        val pageSize = 3
                        val pages = followingUsers.chunked(pageSize)
                        val pagerState = rememberPagerState(pageCount = { pages.size })

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp),
                            pageSpacing = 0.dp
                        ) { pageIndex ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                pages[pageIndex].forEach { user ->
                                    FollowingUserItem(user = user)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            pages.indices.forEach { index ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(if (pagerState.currentPage == index) 8.dp else 6.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (pagerState.currentPage == index) Color.Black
                                            else Color.LightGray
                                        )
                                )
                            }
                        }
                    }
                }
            }

            // 5. 하단 회원 가입일 섹션
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5))
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "회원 가입일: 2025년 9월",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// ✅ 변경된 부분: CircleShape → RoundedCornerShape(8.dp)
@Composable
fun FollowingUserItem(user: FollowingUser) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "${user.firstName} 프로필",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(107.dp)
                .height(106.dp)
                .clip(RoundedCornerShape(0.dp))  // ✅ 변경: CircleShape → RoundedCornerShape(8.dp)
                .background(Color(0xFFE0E0E0))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${user.firstName}\n${user.lastName}",
            fontSize = 11.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun MenuIconItem(iconResId: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Composable
fun MenuDivider() {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(1.dp)
            .background(Color.LightGray.copy(alpha = 0.5f))
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}