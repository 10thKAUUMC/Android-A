package com.example.week2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week2.databinding.FragmentWishlistBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var productManager: ProductManager
    private lateinit var wishlistAdapter: HomeProductAdapter

    // 🌟 추가: DataStore 업데이트를 위한 현재 리스트 보관 변수
    private var currentList: List<ProductData> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWishlistBinding.bind(view)

        productManager = ProductManager(requireContext())

        // 🌟 수정: 어댑터에 하트 클릭 이벤트(onHeartClick) 추가
        wishlistAdapter = HomeProductAdapter(emptyList()) { clickedProduct ->
            val updatedList = currentList.map {
                if (it.id == clickedProduct.id) {
                    it.copy(isLiked = !it.isLiked) // 하트 상태 반전
                } else {
                    it
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                productManager.saveProducts(updatedList) // DataStore에 저장
            }
        }

        binding.rvWishlist.apply {
            adapter = wishlistAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productManager.getProducts().collectLatest { products ->
                currentList = products // 🌟 전체 최신 데이터 백업

                // 🌟 화면에 보여줄 때는 하트가 켜진(isLiked == true) 상품만 필터링!
                val likedProducts = products.filter { it.isLiked }
                wishlistAdapter.updateData(likedProducts)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}