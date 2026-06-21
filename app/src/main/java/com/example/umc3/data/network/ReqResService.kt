package com.example.umc3.data.network

import com.example.umc3.data.network.dto.UserListResponse
import com.example.umc3.data.network.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqResService {

    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int = 1): UserListResponse
}
