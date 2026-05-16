//package com.example.week2.ui.profile
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.bumptech.glide.Glide
//import com.example.week2.R
//import com.example.week2.databinding.FragmentProfileBinding
//import com.example.week2.ui.adapter.FollowingAdapter
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class ProfileFragment : Fragment(R.layout.fragment_profile) {
//
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: ProfileViewModel by viewModels()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentProfileBinding.bind(view)
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.uiState.collectLatest { state ->
//                state.user?.let { user ->
//                    binding.tvNickname.text = "${user.firstName} ${user.lastName}"
//                    Glide.with(this@ProfileFragment)
//                        .load(user.avatar)
//                        .circleCrop()
//                        .into(binding.ivProfile)
//                }
//
//                if (state.followingList.isNotEmpty()) {
//                    binding.rvFollowing.adapter = FollowingAdapter(state.followingList)
//                    binding.rvFollowing.layoutManager =
//                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                    binding.tvFollowingTitle.text = "팔로잉 (${state.followingList.size})"
//                }
//
//                if (!state.isLoading && state.user == null) {
//                    Toast.makeText(requireContext(), "유저 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
