package com.example.chapter1
import com.example.week2.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.databinding.FragmentProfileBinding
import com.example.chapter1.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리사이클러뷰 초기화
        followingAdapter = FollowingAdapter(emptyList())
        binding.rvFollowing.apply {
            adapter = followingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        // uiState 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.nickname.isNotEmpty()) {
                        binding.tvNickname.text = state.nickname
                    }
                    followingAdapter.updateList(state.followingList)
                    state.error?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener { Toast.makeText(context, "프로필 수정", Toast.LENGTH_SHORT).show() }
        binding.btnOrder.setOnClickListener { Toast.makeText(context, "주문", Toast.LENGTH_SHORT).show() }
        binding.btnPass.setOnClickListener { Toast.makeText(context, "패스", Toast.LENGTH_SHORT).show() }
        binding.btnEvent.setOnClickListener { Toast.makeText(context, "이벤트", Toast.LENGTH_SHORT).show() }
        binding.btnSetting.setOnClickListener { Toast.makeText(context, "설정", Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}