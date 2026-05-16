//package com.example.week2
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.week2.databinding.FragmentShopTabAllBinding
//import com.example.week2.ui.home.HomeViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class ShopTabAllFragment : Fragment(R.layout.fragment_shop_tab_all) {
//
//    private var _binding: FragmentShopTabAllBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: HomeViewModel by viewModels(
//        ownerProducer = { requireParentFragment() }
//    )
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentShopTabAllBinding.bind(view)
//
//        val adapter = HomeProductAdapter(emptyList()) { product ->
//            viewModel.toggleLike(product)
//        }
//
//        binding.rvShop.apply {
//            this.adapter = adapter
//            layoutManager = GridLayoutManager(requireContext(), 2)
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.uiState.collectLatest { state ->
//                adapter.updateData(state.products)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
