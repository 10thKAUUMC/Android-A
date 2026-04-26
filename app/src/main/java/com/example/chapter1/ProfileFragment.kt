package com.example.chapter1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chapter1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    // ViewBinding 설정
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 각 커스텀 버튼에 대한 개별 클릭 리스너 설정
        binding.btnOrder.setOnClickListener {
            Toast.makeText(context, "주문 클릭됨", Toast.LENGTH_SHORT).show()
        }

        binding.btnPass.setOnClickListener {
            Toast.makeText(context, "패스 클릭됨", Toast.LENGTH_SHORT).show()
        }

        binding.btnEvent.setOnClickListener {
            Toast.makeText(context, "이벤트 클릭됨", Toast.LENGTH_SHORT).show()
        }

        binding.btnSetting.setOnClickListener {
            Toast.makeText(context, "설정 클릭됨", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}