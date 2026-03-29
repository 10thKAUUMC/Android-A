package com.example.chapter1 // 본인의 패키지명 유지

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 이 아래에 제가 드린 HomeAdapter 코드를 붙여넣으세요.
class HomeAdapter(private val itemList: List<HomeItem>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val price: TextView = view.findViewById(R.id.itemTitle2)
        val image: ImageView = view.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.price.text = item.price
        holder.image.setImageResource(item.imageRes)
    }

    override fun getItemCount() = itemList.size
}