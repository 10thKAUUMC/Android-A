package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.week2.databinding.FragmentShopBinding
import com.google.android.material.tabs.TabLayoutMediator

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    // 🌟 탭에 들어갈 이름들을 배열로 준비합니다.
    private val tabTitles = arrayOf("전체", "Top & T-shirts", "Sale")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShopBinding.bind(view)

        // 1. 뷰페이저에 우리가 만든 탭 어댑터(ShopPagerAdapter)를 연결합니다.
        binding.vpShop.adapter = ShopPagerAdapter(this)

        // 2. TabLayoutMediator를 통해 탭(tabShop)과 뷰페이저(vpShop)를 묶어줍니다!
        TabLayoutMediator(binding.tabShop, binding.vpShop) { tab, position ->
            tab.text = tabTitles[position] // 준비한 배열에서 이름을 꺼내 탭에 세팅
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}