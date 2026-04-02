package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week2.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWishlistBinding.bind(view)

        // 1. 위시리스트 더미 데이터 생성
        val wishList = mutableListOf(
            ProductData("Air Jordan XXXVI", "US$185", R.drawable.air_jordan),
            ProductData("Nike Air Force 1'07", "US$115", R.drawable.nike_air_force)
        )

        // 2. 어댑터 설정
        // 홈/구매하기에서 썼던 HomeProductAdapter를 그대로 쓰거나,
        // 위시리스트용 디자인이 다르다면 새 어댑터를 연결하세요.
        val wishlistAdapter = HomeProductAdapter(wishList)

        // 3. 리사이클러뷰 설정 (핵심: LinearLayoutManager - 세로 리스트)
        binding.rvWishlist.apply {
            adapter = wishlistAdapter
            // 기본값이 세로(Vertical)이므로 아래처럼 설정하면 한 줄씩 나옵니다.
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}