package com.example.app08_menu.customMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app08_menu.databinding.ItemRecyclerviewBinding

class MyAdapter(val dataList:MutableList<String>) : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    class MyHolder(val binding: ItemRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.binding.itemData.text = dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}