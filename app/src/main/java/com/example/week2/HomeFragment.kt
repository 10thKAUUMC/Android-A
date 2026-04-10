package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week2.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productManager: ProductManager
    private lateinit var homeAdapter: HomeProductAdapter

    // DataStore에 다시 저장할 때 쓰기 위해 현재 리스트를 보관할 변수
    private var currentList: List<ProductData> = emptyList()

    // 🌟 onViewCreated는 프래그먼트당 딱 한 번만 있어야 합니다!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        productManager = ProductManager(requireContext())

        // 1. 어댑터 초기화 (하트 클릭 이벤트 포함)
        homeAdapter = HomeProductAdapter(emptyList(), showHeart = false, isLargeSize = true)

        // 2. 리사이클러뷰 설정
        binding.rvHomeProduct.adapter = homeAdapter
        binding.rvHomeProduct.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // 3. DataStore 데이터 관찰 및 UI 갱신
        viewLifecycleOwner.lifecycleScope.launch {
            productManager.getProducts().collectLatest { products ->
                // 불러온 최신 데이터를 currentList에 백업
                currentList = products

                if (products.isEmpty()) {
                    // 최초 실행 시 더미 데이터 세팅
                    val initialData = listOf(
                        ProductData(1, "Air Jordan XXXVI", "US$185", R.drawable.air_jordan, false),
                        ProductData(2, "Nike Air Force 1'07", "US$115", R.drawable.nike_air_force, false)
                    )
                    productManager.saveProducts(initialData)
                } else {
                    // 데이터가 있으면 어댑터 업데이트
                    homeAdapter.updateData(products)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}