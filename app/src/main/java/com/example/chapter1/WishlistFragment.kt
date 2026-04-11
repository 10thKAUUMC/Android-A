package com.example.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chapter1.databinding.FragmentWishlistBinding
import androidx.lifecycle.lifecycleScope
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

        // 1. 어댑터 초기화 (위시리스트에서 하트를 해제할 경우 처리)
        homeAdapter = HomeAdapter(mutableListOf(), true) { updatedWishlist ->
            viewLifecycleOwner.lifecycleScope.launch {
                // 위시리스트에서 하트 해제 시 바로 저장 (리스트에서 사라짐)
                val onlyFavorite = updatedWishlist.filter { it.wish == R.drawable.ic_heart_on }
                settingsManager.saveWishListItems(onlyFavorite)

                // [선택사항] 쇼핑 리스트의 하트 상태도 해제하고 싶다면 여기서 쇼핑 리스트 Flow를 수정하는 로직 추가 가능
            }
        }

        binding.recWhishlist.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        // 2. DataStore 관찰 (위시리스트 데이터 표시)
        viewLifecycleOwner.lifecycleScope.launch {
            settingsManager.wishListFlow.collect { dataList ->
                // 위시리스트이므로 하트가 켜진 데이터만 걸러서 보여줌 (안전장치)
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