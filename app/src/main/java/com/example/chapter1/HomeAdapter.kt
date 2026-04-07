package com.example.chapter1 // 본인의 패키지명 유지

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

// 이 아래에 제가 드린 HomeAdapter 코드를 붙여넣으세요.
// 1. List -> MutableList로 변경하여 수정 가능하게 만듭니다.
class HomeAdapter(private var itemList: MutableList<HomeItem>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    // 2. 이제 clear()와 addAll()이 정상적으로 작동합니다.
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
    }

    override fun getItemCount() = itemList.size
}