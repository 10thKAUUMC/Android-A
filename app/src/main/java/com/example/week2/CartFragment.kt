package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.week2.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_cart) {

    // ViewBinding을 위한 변수 선언
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 레이아웃과 바인딩 연결
        _binding = FragmentCartBinding.bind(view)

        // 미션: '주문하기' 버튼 클릭 시 '구매하기' 탭으로 이동
        // XML에서 버튼 ID를 btn_order로 만들었으므로 binding.btnOrder로 접근합니다.
        binding.btnOrder.setOnClickListener {
            // 현재 프래그먼트를 호스팅하는 Activity를 MainActivity로 캐스팅하여 함수 호출
            (activity as? MainActivity)?.changeTab(R.id.shopFragment)
        }
    }

    // 프래그먼트의 View가 파괴될 때 바인딩 객체 해제 (메모리 누수 방지)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}