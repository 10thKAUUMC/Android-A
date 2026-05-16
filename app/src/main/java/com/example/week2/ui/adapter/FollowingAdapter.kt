//package com.example.week2.ui.adapter // 본인 패키지 경로 확인
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.week2.data.model.UserData // 내가 만든 데이터 클래스 가져오기
//import com.example.week2.databinding.ItemFollowingBinding // 자동 생성된 바인딩 클래스
//
//class FollowingAdapter(private val userList: List<UserData>) :
//    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
//
//    // 1. ViewHolder 정의: item_following.xml의 뷰들을 담아두는 보관함
//    inner class ViewHolder(val binding: ItemFollowingBinding) : RecyclerView.ViewHolder(binding.root)
//
//    // 2. 화면 생성: 아이템 하나가 들어갈 틀(Layout)을 만듭니다.
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemFollowingBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return ViewHolder(binding)
//    }
//
//    // 3. 데이터 연결: 틀 안에 실제 유저 데이터를 채워 넣습니다.
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val user = userList[position]
//
//        // 텍스트 연결 (이름 + 성)
//        holder.binding.tvFollowingName.text = "${user.firstName} ${user.lastName}"
//
//        // 이미지 연결 (Glide 사용)
//        Glide.with(holder.itemView.context)
//            .load(user.avatar)
//            .circleCrop() // 원형으로 깎기
//            .into(holder.binding.ivFollowingProfile)
//    }
//
//    // 4. 아이템 개수 확인
//    override fun getItemCount() = userList.size
//}