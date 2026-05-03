package com.example.week2.data.network
// 1. Retrofit 관련 도구들 가져오기
import retrofit2.Response
import retrofit2.http.GET

// 2. 내가 만든 데이터 클래스(DTO) 가져오기 (매우 중요!)
// com.example.week2 부분은 본인의 실제 패키지명으로 수정해야 할 수 있습니다.
import com.example.week2.data.model.UserData
import com.example.week2.data.model.UserListResponse
import com.example.week2.data.model.UserResponse

interface MyPageService {
    // 1번 유저(나) 정보 가져오기
    @GET("api/users/1")
    suspend fun getUserInfo(): Response<UserResponse<UserData>>

    // 팔로잉 리스트(전체 유저) 가져오기
    @GET("api/users")
    suspend fun getFollowingList(): Response<UserListResponse>
}