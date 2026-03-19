package com.example.myapplication
// PR
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import com.example.myapplication.databinding.LayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: LayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivStampLaugh.setOnClickListener {
            binding.textView7.setTextColor(Color.parseColor("#FFD700"))
        }

        binding.ivStampSmile.setOnClickListener {
            binding.textView7.setTextColor(Color.parseColor("#4A90E2"))
        }

        binding.ivStampNeutral.setOnClickListener {
            binding.textView7.setTextColor(Color.parseColor("#A36FFF"))
        }

        binding.ivStampSad.setOnClickListener {
            binding.textView7.setTextColor(Color.parseColor("#4CAF50"))
        }

        binding.ivStampAngry.setOnClickListener {
            binding.textView7.setTextColor(Color.parseColor("#FF4B4B"))
        }

    }
}