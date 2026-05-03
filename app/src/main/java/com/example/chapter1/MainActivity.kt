package com.example.chapter1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chapter1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    override fun onStart() {
//        super.onStart()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fragmentContainer, HomeFragment())
//            .commit()
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedTitle = intent.getStringExtra("title_key") ?: "Discover"

        if (savedInstanceState == null) {
            // 2. HomeFragment 인스턴스 생성 및 데이터 담기
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("home_title", receivedTitle)
            homeFragment.arguments = bundle


            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragmentContainer, homeFragment)
                .commit()
        }





            //BottomNavigationView를 눌렀을 때 Fragment 변경하기
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {

                //매인 화면
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, HomeFragment())
                        .commit()
                    true
                }

                //일기 작성 화면
                R.id.diaryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, ShoppingFragment())
                        .commit()
                    true
                }

                //일기 히스토리 화면
                R.id.calendarFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, WishlistFragment())
                        .commit()
                    true
                }

                //친구 화면
                R.id.friendFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragmentContainer, BagFragment())
                        .commit()
                    true
                }

                //마이페이지 화면
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
