package com.example.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chapter1.databinding.FragmentShoppingBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first // Flow의 현재 값을 한 번만 가져오기 위해 추가

class ShoppingFragment : Fragment() {
    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsManager: SettingsManager
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsManager = SettingsManager(requireContext())

        // 1. 어댑터 초기화 (하트 클릭 시 쇼핑리스트 + 위시리스트 동시 업데이트)
        homeAdapter = HomeAdapter(mutableListOf(), false) { updatedShoppingList ->
            viewLifecycleOwner.lifecycleScope.launch {
                // (1) 현재 쇼핑 리스트 상태 저장
                settingsManager.saveShoppingItems(updatedShoppingList)

                // (2) 위시리스트 동기화 로직
                // 현재 저장된 위시리스트를 가져옴
                val currentWishlist = settingsManager.wishListFlow.first().toMutableList()

                // 클릭된 아이템의 최신 상태 확인 (가장 최근에 변경된 리스트에서 찾음)
                // 실제 앱에서는 id값으로 찾는 것이 좋지만, 현재 구조상 title로 비교합니다.
                updatedShoppingList.forEach { item ->
                    val isInWishlist = currentWishlist.any { it.title == item.title }

                    if (item.wish == R.drawable.ic_heart_on && !isInWishlist) {
                        // 하트가 켜졌는데 위시리스트에 없다면 추가
                        currentWishlist.add(item)
                    } else if (item.wish == R.drawable.ic_heatr_off && isInWishlist) {
                        // 하트가 꺼졌는데 위시리스트에 있다면 삭제
                        currentWishlist.removeAll { it.title == item.title }
                    }
                }

                // (3) 변경된 위시리스트 저장
                settingsManager.saveWishListItems(currentWishlist)
            }
        }

        binding.recShopping.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        // 2. DataStore 관찰 (쇼핑 리스트 표시)
        viewLifecycleOwner.lifecycleScope.launch {
            settingsManager.shoppingItemsFlow.collect { dataList ->
                if (dataList.isEmpty()) {
                    val initialData = listOf(
                        HomeItem("","Nike Everyday Plus", "Training Socks","US$10", R.mipmap.ic_socks2, R.drawable.ic_heatr_off),
                        HomeItem("","Nike Elite Crew", "Basketball Socks","US$16", R.mipmap.ic_socks1, R.drawable.ic_heatr_off),
                        HomeItem("BestSeller","Nike Air Force 1", "Women's Shoes","US$115", R.mipmap.ic_shoe3, R.drawable.ic_heatr_off),
                        HomeItem("BestSeller","Jordan Essentials", "Men's Shoes","US$115", R.mipmap.ic_shoe4, R.drawable.ic_heatr_off)
                    )
                    settingsManager.saveShoppingItems(initialData)
                } else {
                    homeAdapter.setData(dataList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}