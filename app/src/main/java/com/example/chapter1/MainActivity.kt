package com.example.chapter1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chapter1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint  // ← 추가

@AndroidEntryPoint  // ← 추가
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
                .replace(R.id.main_fragmentContainer, homeFragment)
                .commit()
        }

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, HomeFragment())
                        .commit()
                    true
                }
                R.id.diaryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, ShoppingFragment())
                        .commit()
                    true
                }
                R.id.calendarFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, WishlistFragment())
                        .commit()
                    true
                }
                R.id.friendFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, BagFragment())
                        .commit()
                    true
                }
                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}