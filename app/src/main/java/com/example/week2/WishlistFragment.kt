//package com.example.week2
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.week2.databinding.FragmentWishlistBinding
//import com.example.week2.ui.wishlist.WishlistViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class WishlistFragment : Fragment(R.layout.fragment_wishlist) {
//
//    private var _binding: FragmentWishlistBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: WishlistViewModel by viewModels()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentWishlistBinding.bind(view)
//
//        val adapter = HomeProductAdapter(emptyList()) { product ->
//            viewModel.toggleLike(product)
//        }
//
//        binding.recWhishlist.adapter = adapter
//        binding.recWhishlist.layoutManager = GridLayoutManager(requireContext(), 2)
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.uiState.collectLatest { state ->
//                adapter.updateData(state.likedProducts)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
