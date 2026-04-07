package com.example.chapter1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(
    private var itemList: MutableList<HomeItem>,
    private val onHeartClick: (List<HomeItem>) -> Unit // 하트 클릭 시 데이터를 저장하기 위한 콜백
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    fun setData(newList: List<HomeItem>) {
        this.itemList.clear()
        this.itemList.addAll(newList)
        notifyDataSetChanged()
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val seller: TextView = view.findViewById(R.id.itemState)
        val title: TextView = view.findViewById(R.id.itemTitle)
        val price: TextView = view.findViewById(R.id.itemTitle2)
        val subtitle: TextView = view.findViewById(R.id.itemTitle3)
        val image: ImageView = view.findViewById(R.id.itemImage)
        val wish: ImageView = view.findViewById(R.id.wishButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = itemList[position]
        holder.seller.text = item.seller
        holder.title.text = item.title
        holder.price.text = item.price
        holder.subtitle.text = item.subtitle
        holder.image.setImageResource(item.imageRes)

        // 데이터에 저장된 리소스로 이미지 설정
        holder.wish.setImageResource(item.wish)

        // 하트 클릭 이벤트
        holder.wish.setOnClickListener {
            // 이미지 토글 (on <-> off)
            val newWishIcon = if (item.wish == R.drawable.ic_heatr_off) {
                R.drawable.ic_heart_on
            } else {
                R.drawable.ic_heatr_off
            }

            // 1. 현재 리스트의 해당 아이템 수정 (copy 사용)
            itemList[position] = item.copy(wish = newWishIcon)

            // 2. 화면 갱신
            notifyItemChanged(position)

            // 3. 변경된 전체 리스트를 프래그먼트로 전달하여 DataStore에 저장하도록 함
            onHeartClick(itemList)
        }
    }

    override fun getItemCount() = itemList.size
}