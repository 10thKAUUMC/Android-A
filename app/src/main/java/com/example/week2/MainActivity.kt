//package com.example.week2
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.week2.databinding.ActivityMainBinding
//import com.example.week2.ui.profile.ProfileFragment
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, HomeFragment())
//                .commit()
//        }
//
//        binding.mainBnv.setOnItemSelectedListener { item ->
//            val fragment = when (item.itemId) {
//                R.id.homeFragment -> HomeFragment()
//                R.id.shopFragment -> ShopAllFragment()
//                R.id.wishlistFragment -> WishlistFragment()
//                R.id.cartFragment -> CartFragment()
//                R.id.profileFragment -> ProfileFragment()
//                else -> null
//            }
//
//            fragment?.let {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_frm, it)
//                    .commit()
//                true
//            } ?: false
//        }
//    }
//
//    fun changeTab(itemId: Int) {
//        binding.mainBnv.selectedItemId = itemId
//    }
//}
