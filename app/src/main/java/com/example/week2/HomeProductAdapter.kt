package com.example.week2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2.databinding.ItemProductHomeBinding

class HomeProductAdapter(
    private var productList: List<ProductData>,
    private val showHeart: Boolean = true,
    private val isLargeSize: Boolean = false, // 🌟 1. 크기를 키울지 말지 결정하는 스위치 추가 (기본값 false)
    private val onHeartClick: (ProductData) -> Unit = {}
) : RecyclerView.Adapter<HomeProductAdapter.HomeViewHolder>() {

    class HomeViewHolder(
        val binding: ItemProductHomeBinding,
        val onHeartClick: (ProductData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        // 🌟 2. bind 함수에서 isLargeSize 값도 받도록 추가
        fun bind(product: ProductData, showHeart: Boolean, isLargeSize: Boolean) {
            binding.ivProduct.setImageResource(product.imageRes)
            binding.tvName.text = product.name
            binding.tvPrice.text = product.price

            // 하트 숨기기/보여주기 로직
            if (showHeart) {
                binding.ivHeart.visibility = View.VISIBLE
                if (product.isLiked) {
                    binding.ivHeart.setImageResource(R.drawable.heart)
                } else {
                    binding.ivHeart.setImageResource(R.drawable.pureheart)
                }
            } else {
                binding.ivHeart.visibility = View.GONE
            }

            binding.ivHeart.setOnClickListener {
                onHeartClick(product)
            }

            // 🌟 3. 코드에서 직접 크기를 조절하는 마법!
            val context = binding.root.context
            val density = context.resources.displayMetrics.density // dp를 픽셀로 변환하기 위한 도구

            val rootParams = binding.root.layoutParams
            val imageParams = binding.ivProduct.layoutParams

            if (isLargeSize) {
                // 홈 화면 (스위치 ON): 400dp로 강제 확대
                val size360 = (360 * density).toInt()
                rootParams.width = size360
                imageParams.height = size360
            } else {
                // 구매하기/위시리스트 (스위치 OFF): 원래 도면대로 160dp 유지
                val size160 = (160 * density).toInt()
                rootParams.width = size160
                imageParams.height = size160
            }

            binding.root.layoutParams = rootParams
            binding.ivProduct.layoutParams = imageParams
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemProductHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding, onHeartClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        // 🌟 4. 스위치 값을 전달
        holder.bind(productList[position], showHeart, isLargeSize)
    }

    override fun getItemCount(): Int = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<ProductData>) {
        this.productList = newData
        notifyDataSetChanged()
    }
}