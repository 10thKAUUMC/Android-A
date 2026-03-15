package com.example.chapter1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chapter1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 바인딩 초기화 (이게 가장 먼저 와야 합니다)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 2. 반드시 binding.root를 사용해 화면을 설정 (중복 setContentView 삭제)
        setContentView(binding.root)

        enableEdgeToEdge()

        // 3. 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. 클릭 리스너 설정
        binding.happyButton.setOnClickListener {
            // XML 파일에 적힌 ID가 textViewUnderText라면 아래와 같이 작성하세요
            binding.underText1.setTextColor(android.graphics.Color.YELLOW)
            binding.underText2.setTextColor(android.graphics.Color.YELLOW)
            binding.underText3.setTextColor(android.graphics.Color.YELLOW)
            binding.underText4.setTextColor(android.graphics.Color.YELLOW)
            binding.underText5.setTextColor(android.graphics.Color.YELLOW)
        }

        binding.excitingButton.setOnClickListener {
            // XML 파일에 적힌 ID가 textViewUnderText라면 아래와 같이 작성하세요
            binding.underText1.setTextColor(android.graphics.Color.BLUE)
            binding.underText2.setTextColor(android.graphics.Color.BLUE)
            binding.underText3.setTextColor(android.graphics.Color.BLUE)
            binding.underText4.setTextColor(android.graphics.Color.BLUE)
            binding.underText5.setTextColor(android.graphics.Color.BLUE)
        }

        binding.normalButton.setOnClickListener {
            // XML 파일에 적힌 ID가 textViewUnderText라면 아래와 같이 작성하세요
            binding.underText1.setTextColor(android.graphics.Color.MAGENTA)
            binding.underText2.setTextColor(android.graphics.Color.MAGENTA)
            binding.underText3.setTextColor(android.graphics.Color.MAGENTA)
            binding.underText4.setTextColor(android.graphics.Color.MAGENTA)
            binding.underText5.setTextColor(android.graphics.Color.MAGENTA)
        }

        binding.nervousButton.setOnClickListener {
            // XML 파일에 적힌 ID가 textViewUnderText라면 아래와 같이 작성하세요
            binding.underText1.setTextColor(android.graphics.Color.GREEN)
            binding.underText2.setTextColor(android.graphics.Color.GREEN)
            binding.underText3.setTextColor(android.graphics.Color.GREEN)
            binding.underText4.setTextColor(android.graphics.Color.GREEN)
            binding.underText5.setTextColor(android.graphics.Color.GREEN)
        }

        binding.happyButton.setOnClickListener {
            // XML 파일에 적힌 ID가 textViewUnderText라면 아래와 같이 작성하세요
            binding.underText1.setTextColor(android.graphics.Color.RED)
            binding.underText2.setTextColor(android.graphics.Color.RED)
            binding.underText3.setTextColor(android.graphics.Color.RED)
            binding.underText4.setTextColor(android.graphics.Color.RED)
            binding.underText5.setTextColor(android.graphics.Color.RED)
        }
    }
}
