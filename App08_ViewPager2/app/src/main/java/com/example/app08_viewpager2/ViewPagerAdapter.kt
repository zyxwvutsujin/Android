package com.example.app08_viewpager2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app08_viewpager2.databinding.ItemViewpagerBinding

//RecyclerView 이용
class ViewPagerAdapter(val listData:MutableList<DataPage>)
    :RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>()  {
    class ViewHolder(val binding:ItemViewpagerBinding)
          :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewHolder {
        return  ViewHolder(ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewHolder, position: Int) {
        var dpage = listData.get(position)
        holder.binding.tvTitle.text =dpage.title
        holder.binding.recylerLayout.setBackgroundColor(dpage.color)
    }

    override fun getItemCount(): Int {
        return  listData.size
    }
}