package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week2.databinding.FragmentShopBinding

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShopBinding.bind(view)

        // 1. 더미 데이터 생성 (Figma 시안을 참고하여 데이터 추가)
        val productList = mutableListOf(
            ProductData("Air Jordan XXXVI", "US$185", R.drawable.air_jordan),
            ProductData("Nike Air Force 1'07", "US$115", R.drawable.nike_air_force)
        )

        // 2. 어댑터 인스턴스 생성 (Home에서 만든 어댑터를 재활용하거나 새로 만듭니다)
        // 여기서는 HomeProductAdapter를 재활용하는 예시입니다.
        val shopAdapter = HomeProductAdapter(productList)

        // 3. 리사이클러뷰 설정 (핵심: GridLayoutManager)
        binding.rvShop.apply {
            adapter = shopAdapter
            // requireContext(): 프래그먼트에서 context를 가져옴
            // spanCount = 2: 한 줄에 2개씩 보여줌
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}