package com.example.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chapter1.databinding.FragmentWishlistBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsManager: SettingsManager
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsManager = SettingsManager(requireContext())

        // 1. 어댑터 초기화 (위시리스트에서 하트 클릭 시 처리)
        homeAdapter = HomeAdapter(mutableListOf(), true) { updatedWishlist ->
            viewLifecycleOwner.lifecycleScope.launch {
                // (1) 하트가 켜진 아이템만 필터링하여 위시리스트 업데이트
                val onlyFavorites = updatedWishlist.filter { it.wish == R.drawable.ic_heart_on }
                settingsManager.saveWishListItems(onlyFavorites)

                // (2) 쇼핑 리스트(ShoppingFragment)와 하트 상태 동기화
                // 쇼핑 리스트의 전체 데이터를 가져옵니다.
                val currentShoppingList = settingsManager.shoppingItemsFlow.first().toMutableList()

                // 위시리스트에서 하트가 꺼진 아이템을 찾아 쇼핑 리스트에서도 하트를 끕니다.
                updatedWishlist.filter { it.wish == R.drawable.ic_heatr_off }.forEach { unlikedItem ->
                    val index = currentShoppingList.indexOfFirst { it.title == unlikedItem.title }
                    if (index != -1) {
                        currentShoppingList[index] = currentShoppingList[index].copy(wish = R.drawable.ic_heatr_off)
                    }
                }

                // 업데이트된 쇼핑 리스트 저장
                settingsManager.saveShoppingItems(currentShoppingList)
            }
        }

        binding.recWhishlist.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        // 2. DataStore 관찰 (위시리스트 데이터 표시)
        viewLifecycleOwner.lifecycleScope.launch {
            settingsManager.wishListFlow.collect { dataList ->
                // 위시리스트 화면이므로 하트가 ON인 데이터만 표시
                val wishlistOnly = dataList.filter { it.wish == R.drawable.ic_heart_on }
                homeAdapter.setData(wishlistOnly)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}