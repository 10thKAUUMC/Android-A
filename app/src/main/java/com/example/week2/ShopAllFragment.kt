//package com.example.week2
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.example.week2.databinding.FragmentShopAllBinding
//import com.example.week2.ui.home.HomeViewModel
//import com.google.android.material.tabs.TabLayoutMediator
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class ShopAllFragment : Fragment(R.layout.fragment_shop_all) {
//
//    private var _binding: FragmentShopAllBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: HomeViewModel by viewModels()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentShopAllBinding.bind(view)
//
//        binding.viewPager.adapter = ShopPagerAdapter(this)
//
//        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
//            tab.text = when (position) {
//                0 -> "전체"
//                1 -> "Tops & T-Shirts"
//                else -> "Sale"
//            }
//        }.attach()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
