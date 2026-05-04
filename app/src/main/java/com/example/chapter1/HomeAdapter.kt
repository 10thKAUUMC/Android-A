package com.example.chapter1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(
    private var itemList: MutableList<HomeItem>,
    private val isWishlistPage: Boolean = false,
    private val onHeartClick: (List<HomeItem>) -> Unit
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

        // --- 하트 아이콘 제어 로직 수정 ---
        // 위시리스트 페이지든 쇼핑 페이지든 하트 버튼을 보이게 설정합니다.
        holder.wish.visibility = View.VISIBLE
        holder.wish.setImageResource(item.wish)

        // 하트 클릭 이벤트
        holder.wish.setOnClickListener {
            val newWishIcon = if (item.wish == R.drawable.ic_heatr_off) {
                R.drawable.ic_heart_on
            } else {
                R.drawable.ic_heatr_off
            }

            // 데이터 업데이트
            itemList[position] = item.copy(wish = newWishIcon)
            notifyItemChanged(position)

            // 변경된 전체 리스트를 프래그먼트로 전달
            onHeartClick(itemList)
        }
    }

    override fun getItemCount() = itemList.size
}