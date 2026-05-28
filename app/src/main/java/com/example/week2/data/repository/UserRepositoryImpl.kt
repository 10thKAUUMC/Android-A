//package com.example.week2.data.repository
//
//import com.example.week2.data.model.UserData
//import com.example.week2.data.network.MyPageService
//import com.example.week2.domain.repository.UserRepository
//import javax.inject.Inject
//
//class UserRepositoryImpl @Inject constructor(
//    private val service: MyPageService
//) : UserRepository {
//
//    override suspend fun getUserInfo(): UserData? {
//        return try {
//            val response = service.getUserInfo()
//            if (response.isSuccessful) response.body()?.data else null
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//    override suspend fun getFollowingList(): List<UserData> {
//        return try {
//            val response = service.getFollowingList()
//            if (response.isSuccessful) response.body()?.data ?: emptyList() else emptyList()
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//}
