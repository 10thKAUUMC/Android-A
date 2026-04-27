package com.example.chapter1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.databinding.FragmentProfileBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val API_KEY = "reqres_468c6f71952646528b8234815e9906b4"
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 리사이클러뷰 초기화 (가로 방향)
        followingAdapter = FollowingAdapter(emptyList())
        binding.rvFollowing.apply {
            adapter = followingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        // 2. 데이터 가져오기
        fetchData()

        // 3. 버튼 클릭 리스너
        setupClickListeners()
    }

    private fun fetchData() {
        // 공통 인증 클라이언트
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", API_KEY)
                .build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ReqResService::class.java)

        // API 호출 A: 상단 닉네임
        service.getUser().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()?.data
                    binding.tvNickname.text = "${user?.firstName} ${user?.lastName}"
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("API_ERROR", "Nickname load failed")
            }
        })

        // API 호출 B: 하단 팔로잉 이미지 리스트
        service.getUserList().enqueue(object : Callback<UserListResponse> {
            override fun onResponse(call: Call<UserListResponse>, response: Response<UserListResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    followingAdapter.updateList(users)
                }
            }
            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                Log.e("API_ERROR", "User list load failed")
            }
        })
    }

    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener { Toast.makeText(context, "프로필 수정", Toast.LENGTH_SHORT).show() }
        binding.btnOrder.setOnClickListener { Toast.makeText(context, "주문", Toast.LENGTH_SHORT).show() }
        binding.btnPass.setOnClickListener { Toast.makeText(context, "패스", Toast.LENGTH_SHORT).show() }
        binding.btnEvent.setOnClickListener { Toast.makeText(context, "이벤트", Toast.LENGTH_SHORT).show() }
        binding.btnSetting.setOnClickListener { Toast.makeText(context, "설정", Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}