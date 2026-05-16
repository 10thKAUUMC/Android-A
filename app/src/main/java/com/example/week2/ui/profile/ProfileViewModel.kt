//package com.example.week2.ui.profile
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.week2.data.model.UserData
//import com.example.week2.domain.repository.UserRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ProfileViewModel @Inject constructor(
//    private val userRepository: UserRepository
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow(ProfileUiState())
//    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
//
//    init {
//        loadMypageData()
//    }
//
//    private fun loadMypageData() {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//            val user = userRepository.getUserInfo()
//            val following = userRepository.getFollowingList()
//            _uiState.update {
//                it.copy(
//                    isLoading = false,
//                    user = user,
//                    followingList = following
//                )
//            }
//        }
//    }
//}
//
//data class ProfileUiState(
//    val isLoading: Boolean = false,
//    val user: UserData? = null,
//    val followingList: List<UserData> = emptyList()
//)
