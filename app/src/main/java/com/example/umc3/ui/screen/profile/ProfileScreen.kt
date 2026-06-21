package com.example.umc3.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.umc3.R
import com.example.umc3.data.UserRepository
import com.example.umc3.data.network.dto.User
import kotlinx.coroutines.async

private val ScreenBackground = Color(0xFFF5F5F5)
private val CardBackground = Color.White
private val PlaceholderGray = Color(0xFFD9D9D9)
private val DividerGray = Color(0xFFEEEEEE)
private val SubTextGray = Color(0xFF767676)
private val BorderGray = Color(0xFFE5E5E5)

@Composable
fun ProfileScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var me by remember { mutableStateOf<User?>(null) }
    var following by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        // 두 호출은 서로 독립적이므로 async로 동시에 띄워 대기 시간을 절반으로 줄인다.
        // (LaunchedEffect 블록 자체가 CoroutineScope라 async를 바로 호출할 수 있다.)
        val userDeferred = async { UserRepository.getUser(1) }
        val listDeferred = async { UserRepository.getUsers() }
        val userResult = userDeferred.await()
        val listResult = listDeferred.await()

        userResult.onSuccess { me = it }
            .onFailure { errorMessage = it.message ?: "유저 정보를 불러오지 못했습니다." }

        listResult.onSuccess { users ->
            following = users.filter { it.id != 1 }
        }.onFailure {
            if (errorMessage == null) errorMessage = it.message ?: "팔로잉 리스트를 불러오지 못했습니다."
        }

        isLoading = false
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground),
    ) {
        when {
            isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator(color = Color.Black) }
            errorMessage != null -> Box(
                modifier = Modifier.fillMaxSize().padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "에러: $errorMessage",
                    color = Color(0xFFE81E26),
                    fontSize = 14.sp,
                )
            }
            else -> ProfileContent(
                user = me,
                following = following,
                topInset = contentPadding.calculateTopPadding(),
                bottomInset = contentPadding.calculateBottomPadding(),
            )
        }
    }
}

@Composable
private fun ProfileContent(
    user: User?,
    following: List<User>,
    topInset: androidx.compose.ui.unit.Dp,
    bottomInset: androidx.compose.ui.unit.Dp,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileHeaderCard(user = user, topInset = topInset)

        Spacer(modifier = Modifier.height(12.dp))

        QuickMenuCard()

        Spacer(modifier = Modifier.height(12.dp))

        MemberBenefitCard()

        Spacer(modifier = Modifier.height(12.dp))

        FollowingSectionCard(following = following)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "회원 가입일: 2025년 9월",
            color = SubTextGray,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(bottomInset))
    }
}

@Composable
private fun ProfileHeaderCard(user: User?, topInset: androidx.compose.ui.unit.Dp) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground)
            .padding(top = topInset + 32.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(PlaceholderGray),
        ) {
            if (user != null) {
                AsyncImage(
                    model = user.avatar,
                    contentDescription = user.fullName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user?.fullName ?: "닉네임",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(width = 180.dp, height = 51.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 1.dp, color = BorderGray, shape = CircleShape)
                .clickable { /* 프로필 수정 */ },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "프로필 수정",
                color = Color.Black,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun QuickMenuCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground)
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuickMenuItem(
            iconRes = R.drawable.archive,
            label = "주문",
            modifier = Modifier.weight(1f),
        )
        VerticalDivider()
        QuickMenuItem(
            iconRes = R.drawable.identificationcard,
            label = "패스",
            modifier = Modifier.weight(1f),
        )
        VerticalDivider()
        QuickMenuItem(
            iconRes = R.drawable.calendarblank,
            label = "이벤트",
            modifier = Modifier.weight(1f),
        )
        VerticalDivider()
        QuickMenuItem(
            iconRes = R.drawable.gear,
            label = "설정",
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun QuickMenuItem(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { /* TODO */ },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = SubTextGray,
            modifier = Modifier.size(28.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.Black,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(32.dp)
            .background(DividerGray),
    )
}

@Composable
private fun MemberBenefitCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground)
            .clickable { /* TODO */ }
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "나이키 멤버 혜택",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "0개 사용 가능",
                color = SubTextGray,
                fontSize = 12.sp,
            )
        }
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "이동",
            tint = SubTextGray,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun FollowingSectionCard(following: List<User>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground)
            .padding(vertical = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "팔로잉 (${following.size})",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "편집",
                color = SubTextGray,
                fontSize = 13.sp,
                modifier = Modifier.clickable { /* TODO */ },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (following.isNotEmpty()) {
            FollowingPager(following = following)
        }
    }
}

@Composable
private fun FollowingPager(following: List<User>) {
    val pagerState = rememberPagerState(pageCount = { following.size })

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(107.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        pageSpacing = 8.dp,
        modifier = Modifier.fillMaxWidth(),
    ) { page ->
        FollowingItem(user = following[page])
    }
}

@Composable
private fun FollowingItem(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = user.avatar,
            contentDescription = user.fullName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 107.dp, height = 106.dp)
                .background(PlaceholderGray),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.firstName,
            color = Color.Black,
            fontSize = 12.sp,
            maxLines = 1,
        )
    }
}
