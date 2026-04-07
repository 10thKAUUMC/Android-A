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
import kotlinx.coroutines.flow.collect

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

        // 1. 어댑터 초기화 (하트 클릭 시 저장하는 로직 포함)
        homeAdapter = HomeAdapter(mutableListOf()) { updatedList ->
            // 하트를 누를 때마다 실행됨
            viewLifecycleOwner.lifecycleScope.launch {
                settingsManager.saveShoppingItems(updatedList)
            }
        }

        binding.recShopping.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        // 2. DataStore 관찰
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