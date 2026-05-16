//package com.example.chapter1.ui.profile
//
//import androidx.lifecycle.ViewModel
//import com.example.chapter1.ReqResService
//import com.example.chapter1.UserData
//import com.example.chapter1.UserListResponse
//import com.example.chapter1.UserResponse
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import javax.inject.Inject
//
//@HiltViewModel
//class ProfileViewModel @Inject constructor(
//    private val reqResService: ReqResService
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow(ProfileUiState())
//    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
//
//    init {
//        fetchNickname()
//        fetchFollowingList()
//    }
//
//    private fun fetchNickname() {
//        reqResService.getUser().enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                if (response.isSuccessful) {
//                    val user = response.body()?.data
//                    _uiState.update { it.copy(nickname = "${user?.firstName} ${user?.lastName}") }
//                }
//            }
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                _uiState.update { it.copy(error = "닉네임 불러오기 실패") }
//            }
//        })
//    }
//
//    private fun fetchFollowingList() {
//        reqResService.getUserList().enqueue(object : Callback<UserListResponse> {
//            override fun onResponse(call: Call<UserListResponse>, response: Response<UserListResponse>) {
//                if (response.isSuccessful) {
//                    val users = response.body()?.data ?: emptyList()
//                    _uiState.update { it.copy(followingList = users) }
//                }
//            }
//            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
//                _uiState.update { it.copy(error = "팔로잉 불러오기 실패") }
//            }
//        })
//    }
//}
//
//data class ProfileUiState(
//    val nickname: String = "",
//    val followingList: List<UserData> = emptyList(),
//    val error: String? = null
//)