package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week2.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    // 1. ViewBinding 설정을 위한 변수
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // 2. 홈 화면 상품 목록 데이터 생성 (더미 데이터)
        val productList = mutableListOf(
            ProductData("Air Jordan XXXVI", "US$185", R.drawable.air_jordan),
            ProductData("Nike Air Force 1'07", "US$115", R.drawable.nike_air_force)
        )

        // 3. 어댑터 연결
        val homeAdapter = HomeProductAdapter(productList)
        binding.rvHomeProduct.adapter = homeAdapter

        // 4. 리사이클러뷰 설정 (중요: 가로 스크롤 설정)
        // LinearLayoutManager의 두 번째 인자를 HORIZONTAL로 설정해야 옆으로 넘겨집니다.
        binding.rvHomeProduct.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    // 5. 바인딩 객체 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}