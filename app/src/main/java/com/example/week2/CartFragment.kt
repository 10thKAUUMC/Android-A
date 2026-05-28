//package com.example.week2
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import com.example.week2.databinding.FragmentBagBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class CartFragment : Fragment(R.layout.fragment_bag) {
//
//    private var _binding: FragmentBagBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentBagBinding.bind(view)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
