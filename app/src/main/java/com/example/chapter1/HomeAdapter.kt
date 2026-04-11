package com.example.chapter1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(
    private var itemList: MutableList<HomeItem>,
    private val isWishlistPage: Boolean = false, // 위시리스트 여부를 판단하는 변수 추가 (기본값 false)
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

        // --- 하트 아이콘 제어 로직 ---
        if (isWishlistPage) {
            // 위시리스트 페이지라면 하트 버튼을 아예 숨김
            holder.wish.visibility = View.GONE
        } else {
            // 쇼핑 페이지라면 하트 버튼을 보이게 하고 리소스 설정
            holder.wish.visibility = View.VISIBLE
            holder.wish.setImageResource(item.wish)
        }

        // 하트 클릭 이벤트 (쇼핑 페이지에서만 작동하도록 조건문 안으로 이동 가능)
        holder.wish.setOnClickListener {
            val newWishIcon = if (item.wish == R.drawable.ic_heatr_off) {
                R.drawable.ic_heart_on
            } else {
                R.drawable.ic_heatr_off
            }
            itemList[position] = item.copy(wish = newWishIcon)
            notifyItemChanged(position)
            onHeartClick(itemList)
        }
    }

    override fun getItemCount() = itemList.size
}