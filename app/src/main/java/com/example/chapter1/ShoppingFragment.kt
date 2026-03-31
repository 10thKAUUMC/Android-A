package com.example.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chapter1.databinding.FragmentHomeBinding
import com.example.chapter1.databinding.FragmentShoppingBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // HomeFragment.kt 내의 onViewCreated 또는 onCreateView 내부
        val recyclerView = binding.recShopping
// 1. 데이터 준비 (이미지 소스 이름은 본인이 가진mipmap/drawable 이름으로 수정하세요)
        val dataList = mutableListOf<HomeItem>()
        dataList.add(HomeItem("","Nike Everyday Plus Cushioned", "Training Ankle Socks (6 Pairs)","US$10", R.mipmap.ic_socks2))
        dataList.add(HomeItem("","Nike Elite Crew", "Basketball Socks\n7 Colours","US$16",R.mipmap.ic_socks1))
        dataList.add(HomeItem("BestSeller","Nike Air Force 1'07", "Women's Shoes\n5 Colours","US$115",R.mipmap.ic_shoe3))
        dataList.add(HomeItem("BestSeller","Jordan ENike Air Force\n1'07ssentials", "Men's Shoes\n2 Colours","US$115",R.mipmap.ic_shoe4))


// 2. 어댑터 및 레이아웃 매니저 설정
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = HomeAdapter(dataList)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoppingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoppingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}