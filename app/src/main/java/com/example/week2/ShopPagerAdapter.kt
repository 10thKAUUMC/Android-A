package com.example.week2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ShopPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShopAllFragment()      // '전체' 화면
            1 -> TopTshirtsFragment()   // 빈 화면 1
            2 -> SaleFragment()         // 빈 화면 2
            else -> ShopFragment()
        }
    }
}