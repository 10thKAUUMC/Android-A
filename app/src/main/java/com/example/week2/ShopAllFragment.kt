package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week2.databinding.FragmentShopAllBinding // 🌟 반드시 이 바인딩인지 확인!
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShopAllFragment : Fragment(R.layout.fragment_shop_all) {

    private var _binding: FragmentShopAllBinding? = null
    private val binding get() = _binding!!

    private lateinit var productManager: ProductManager
    private lateinit var shopAdapter: HomeProductAdapter
    private var currentList: List<ProductData> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShopAllBinding.bind(view)

        productManager = ProductManager(requireContext())

        // 1. 어댑터 초기화 (하트 클릭 기능 포함)
        shopAdapter = HomeProductAdapter(emptyList()) { clickedProduct ->
            val updatedList = currentList.map {
                if (it.id == clickedProduct.id) {
                    it.copy(isLiked = !it.isLiked)
                } else {
                    it
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                productManager.saveProducts(updatedList)
            }
        }

        // 2. 리사이클러뷰 설정 (2줄 그리드)
        binding.rvShop.apply {
            adapter = shopAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        // 3. DataStore 데이터 관찰 (이 부분이 실행되어야 물건이 나타납니다)
        viewLifecycleOwner.lifecycleScope.launch {
            productManager.getProducts().collectLatest { products ->
                currentList = products

                if (products.isEmpty()) {
                    // 🌟 만약 데이터가 아예 없다면 초기 데이터를 넣어줍니다.
                    val initialData = listOf(
                        ProductData(1, "Air Jordan XXXVI", "US$185", R.drawable.air_jordan, false),
                        ProductData(2, "Nike Air Force 1'07", "US$115", R.drawable.nike_air_force, false)
                    )
                    productManager.saveProducts(initialData)
                } else {
                    // 데이터가 있으면 어댑터에 갱신!
                    shopAdapter.updateData(products)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}