package com.example.week2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2.databinding.ItemProductHomeBinding

class HomeProductAdapter(private val productList: List<ProductData>) :
    RecyclerView.Adapter<HomeProductAdapter.HomeViewHolder>() {

    // 1. ViewHolder 정의: item_product_home.xml의 뷰들을 홀딩합니다.
    inner class HomeViewHolder(val binding: ItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductData) {
            binding.ivProduct.setImageResource(product.imageRes)
            binding.tvName.text = product.name
            binding.tvPrice.text = product.price
        }
    }

    // 2. onCreateViewHolder: 뷰 홀더를 처음 만들 때 호출 (레이아웃 인플레이트)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemProductHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    // 3. onBindViewHolder: 생성된 뷰 홀더에 실제 데이터를 결합
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    // 4. getItemCount: 전체 아이템 개수 반환
    override fun getItemCount(): Int = productList.size
}