package com.example.week2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.week2.databinding.ActivityMainBinding
import com.example.week2.HomeFragment
import com.example.week2.ShopFragment
import com.example.week2.WishlistFragment
import com.example.week2.CartFragment
import com.example.week2.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 화면 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commit()
        }

        // 바텀 네비게이션 설정
        binding.mainBnv.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.homeFragment -> HomeFragment()
                R.id.shopFragment -> ShopFragment()
                R.id.wishlistFragment -> WishlistFragment()
                R.id.cartFragment -> CartFragment()
                R.id.profileFragment -> ProfileFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, it)
                    .commit()
                true
            } ?: false
        }
    }
}