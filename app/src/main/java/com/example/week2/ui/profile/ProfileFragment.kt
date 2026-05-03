package com.example.week2.ui.profile // 본인 패키지 경로

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.week2.R
import com.example.week2.data.network.ApiClient
import com.example.week2.databinding.FragmentProfileBinding
import com.example.week2.ui.adapter.FollowingAdapter
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        // 1. 서버에서 데이터 가져오기 시작!
        loadMypageData()
    }

    private fun loadMypageData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 1번 유저 정보 호출
                val response = ApiClient.service.getUserInfo()

                if (response.isSuccessful) {
                    val userData = response.body()?.data
                    if (userData != null) {
                        binding.tvNickname.text = "${userData.firstName} ${userData.lastName}"
                        Glide.with(this@ProfileFragment)
                            .load(userData.avatar)
                            .circleCrop()
                            .into(binding.ivProfile)
                    } else {
                        Toast.makeText(requireContext(), "유저 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "없음"
                    Toast.makeText(requireContext(), "유저 정보 실패 (${response.code()}): $errorMsg", Toast.LENGTH_LONG).show()
                }

                // 팔로잉 리스트 호출
                val listResponse = ApiClient.service.getFollowingList()
                if (listResponse.isSuccessful) {
                    val userList = listResponse.body()?.data ?: emptyList()
                    val adapter = FollowingAdapter(userList)
                    binding.rvFollowing.adapter = adapter
                    binding.rvFollowing.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.tvFollowingTitle.text = "팔로잉 (${userList.size})"
                } else {
                    Toast.makeText(requireContext(), "팔로잉 목록 불러오기 실패 (${listResponse.code()})", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "네트워크 오류: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}