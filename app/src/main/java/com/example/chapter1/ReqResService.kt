package com.example.chapter1

import retrofit2.Call
import retrofit2.http.GET

interface ReqResService {
    // 상단 프로필용 (1번 유저)
    @GET("api/users/1")
    fun getUser(): Call<UserResponse>

    // 하단 리스트용 (유저 목록)
    @GET("api/users?page=1")
    fun getUserList(): Call<UserListResponse>
}