package com.example.chapter1

import com.google.gson.annotations.SerializedName

// 단일 유저용 응답 (상단 닉네임용)
data class UserResponse(
    @SerializedName("data")
    val data: UserData
)

// 유저 리스트용 응답 (하단 팔로잉 이미지 리스트용)
data class UserListResponse(
    @SerializedName("data")
    val data: List<UserData>
)

data class UserData(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("avatar")
    val avatar: String // 유저 프로필 이미지 URL 필드 추가
)