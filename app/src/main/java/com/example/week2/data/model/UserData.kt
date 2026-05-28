//package com.example.week2.data.model
//import com.google.gson.annotations.SerializedName
//
//data class UserResponse<T>(
//    val data: T
//)
//
//// 유저 목록 응답 전용 클래스 (제네릭 중첩 없이 명시적으로 선언)
//data class UserListResponse(
//    val data: List<UserData>
//)
//
//data class UserData(
//    val id: Int,
//    @SerializedName("first_name") val firstName: String,
//    @SerializedName("last_name") val lastName: String,
//    val avatar: String
//)