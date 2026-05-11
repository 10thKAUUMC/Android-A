package com.example.week2.domain.repository

import com.example.week2.data.model.UserData

interface UserRepository {
    suspend fun getUserInfo(): UserData?
    suspend fun getFollowingList(): List<UserData>
}
