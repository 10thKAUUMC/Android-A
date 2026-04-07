package com.example.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chapter1.databinding.FragmentWishlistBinding
import androidx.lifecycle.lifecycleScope // 필수 추가
import kotlinx.coroutines.launch // 필수 추가
import kotlinx.coroutines.flow.collect // 필수 추가

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WishlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WishlistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // 1. 바인딩 객체 선언 (null 허용)
    private var _binding: FragmentWishlistBinding? = null

    // 2. 바인딩 객체에 접근하기 위한 프로퍼티 (!!를 사용하여 매번 null 체크를 하지 않게 함)
    private val binding get() = _binding!!

    // SettingsManager 선언
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        // 4. 바인딩 객체의 루트 뷰를 반환
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsManager = SettingsManager(requireContext())

        // 1. 어댑터 초기화 (빈 리스트)
        val homeAdapter = HomeAdapter(mutableListOf())
        binding.recWhishlist.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        // 2. DataStore 관찰 (Wishlist용)
        viewLifecycleOwner.lifecycleScope.launch {
            settingsManager.wishListFlow.collect { dataList ->
                if (dataList.isEmpty()) {
                    // 데이터가 없으면 초기 위시리스트 저장
                    val initialWishlist = listOf(
                        HomeItem("","Air Jordan 1 Mid", "","US$125", R.mipmap.ic_shoe5),
                        HomeItem("","Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)","US$10", R.mipmap.ic_socks2)
                    )
                    settingsManager.saveWishListItems(initialWishlist)
                } else {
                    // 데이터가 있으면 어댑터 갱신
                    homeAdapter.setData(dataList)
                }
            }
        }

//        // HomeFragment.kt 내의 onViewCreated 또는 onCreateView 내부
//        val recyclerView = binding.recWhishlist
//// 1. 데이터 준비 (이미지 소스 이름은 본인이 가진mipmap/drawable 이름으로 수정하세요)
//        val dataList = mutableListOf<HomeItem>()
//        dataList.add(HomeItem("","Air Jordan 1 Mid", "","US$125", R.mipmap.ic_shoe5))
//        dataList.add(HomeItem("","Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)","US$10", R.mipmap.ic_socks2))
//
//
//// 2. 어댑터 및 레이아웃 매니저 설정
//        recyclerView.layoutManager = GridLayoutManager(context, 2)
//        recyclerView.adapter = HomeAdapter(dataList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WishlistFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WishlistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // 6. 메모리 누수 방지를 위해 뷰가 파괴될 때 바인딩 객체 해제
        _binding = null
    }
}