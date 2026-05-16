package com.example.chapter1
import com.example.week2.R

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week2.databinding.ActivityMainBinding

@AndroidEntryPoint  // ← 추가
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    override fun onStart() {
//        super.onStart()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, HomeFragment())
//            .commit()
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedTitle = intent.getStringExtra("title_key") ?: "Discover"

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("home_title", receivedTitle)
            homeFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, homeFragment)
                .commit()
        }

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commit()
                    true
                }

                //일기 작성 화면
                R.id.shopFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ShoppingFragment())
                        .commit()
                    true
                }

                //일기 히스토리 화면
                R.id.wishlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, WishlistFragment())
                        .commit()
                    true
                }

                //친구 화면
                R.id.cartFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, BagFragment())
                        .commit()
                    true
                }

                //마이페이지 화면
                R.id.profileFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}