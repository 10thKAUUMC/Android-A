package com.example.umc3.data

import com.example.umc3.data.network.ApiClient
import com.example.umc3.data.network.dto.User

object UserRepository {

    private val service = ApiClient.reqResService

    suspend fun getUser(id: Int): Result<User> = runCatching {
        service.getUser(id).data
    }

    suspend fun getUsers(page: Int = 1): Result<List<User>> = runCatching {
        service.getUsers(page).data
    }
}
