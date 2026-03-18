package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import com.example.myapplication.databinding.LayoutBinding // 패키지명 확인!

class MainActivity : AppCompatActivity() {

    private lateinit var binding: LayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 여기서 에러가 난다면 layout.xml 파일이 res/layout 폴더에 있는지 확인하세요.
        binding = LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. ivYellow 같은 ID는 layout.xml 디자인 탭에서 직접 붙여준 이름과 대소문자까지 같아야 합니다.
        binding.ivStampLaugh.setOnClickListener {
            binding.textView7.setTextColor(Color.parseColor("#FFD700"))
        }

        // 2. 파란색 우표 클릭
        binding.ivStampSmile.setOnClickListener {
            binding.textView8.setTextColor(Color.parseColor("#4A90E2"))
        }

        // 3. 보라색 무표정 우표 클릭
        binding.ivStampNeutral.setOnClickListener {
            binding.textView9.setTextColor(Color.parseColor("#A36FFF"))
        }

        // 4. 초록색 시무룩 우표 클릭
        binding.ivStampSad.setOnClickListener {
            binding.textView10.setTextColor(Color.parseColor("#4CAF50"))
        }

        // 5. 빨간색 화난 우표 클릭
        binding.ivStampAngry.setOnClickListener {
            binding.textView11.setTextColor(Color.parseColor("#FF4B4B"))
        }

    }
}