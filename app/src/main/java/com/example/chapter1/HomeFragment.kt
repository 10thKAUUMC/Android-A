package com.example.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import android.widget.Toast
import com.example.chapter1.databinding.FragmentHomeBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var backPressedTime: Long = 0

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // SettingsManager 선언
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)



        }
        // 1번 버튼을 눌렀을 때 HomeFragment로 이동하는 예시

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsManager = SettingsManager(requireContext())

        // 1. 리사이클러뷰 기본 설정
        // 에러 해결: 중괄호 { } 를 추가하여 클릭 시 저장 로직을 넘겨줍니다.
        val homeAdapter = HomeAdapter(mutableListOf()) { updatedList ->
            // 하트 클릭 시 DataStore에 즉시 저장
            viewLifecycleOwner.lifecycleScope.launch {
                settingsManager.saveHomeItems(updatedList)
            }
        }

        binding.recHome.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        // 2. DataStore에서 데이터 관찰 (Collect)
        viewLifecycleOwner.lifecycleScope.launch {
            settingsManager.homeItemsFlow.collect { dataList ->
                if (dataList.isEmpty()) {
                    // 데이터가 하나도 없을 때 초기 데이터 저장 (테스트용)
                    val initialData = listOf(
                        HomeItem("", "Air Jordan XXXVI", "US$185", "", R.mipmap.ic_shoe1,0),
                        HomeItem("", "Nike Air Force 1'07", "US$115", "", R.mipmap.ic_shoe3, 0)
                    )
                    settingsManager.saveHomeItems(initialData)
                } else {
                    // 데이터가 있으면 어댑터에 갱신
                    homeAdapter.setData(dataList) // 어댑터에 데이터를 업데이트하는 함수가 필요합니다
                }
            }
        }
        val callback = object : OnBackPressedCallback(true) { // true: 콜백 활성화
            override fun handleOnBackPressed() {
                // 현재 시각과 이전 시각의 차이가 2초(2000ms) 이내인지 확인
                if (System.currentTimeMillis() - backPressedTime < 2000) {
                    // 2초 이내에 다시 눌렀으므로 액티비티 종료
                    requireActivity().finish()
                } else {
                    // 처음 누르거나 2초가 지났을 때 메시지 출력 및 시간 업데이트
                    Toast.makeText(context, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                    backPressedTime = System.currentTimeMillis()
                }
            }


        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        val title = arguments?.getString("home_title") ?: "Discover"
        binding.tvTitle.text = title

//        // HomeFragment.kt 내의 onViewCreated 또는 onCreateView 내부
//        val recyclerView = binding.recHome
//// 1. 데이터 준비 (이미지 소스 이름은 본인이 가진mipmap/drawable 이름으로 수정하세요)
//        val dataList = mutableListOf<HomeItem>()
//        dataList.add(HomeItem("","Air Jordan XXXVI", "US$185","", R.mipmap.ic_shoe1))
//        dataList.add(HomeItem("","Nike Air Force 1'07", "US$115","",R.mipmap.ic_shoe3))
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // 5. 메모리 누수 방지를 위해 뷰가 파괴될 때 바인딩 해제
        _binding = null
    }
}