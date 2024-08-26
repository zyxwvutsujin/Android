package com.example.app08_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.app08_1.databinding.ItemViewpagerBinding

//RecyclerView 이용
class CustomAdapter(val listData:MutableList<DataPage>)
    :RecyclerView.Adapter<CustomAdapter.ViewHolder>()  {
    class ViewHolder(val binding:ItemViewpagerBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        return  ViewHolder(ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        var dpage = listData.get(position)
        holder.binding.title.text =dpage.title
        holder.binding.recylerLayout.setBackgroundColor(dpage.color)
    }

    override fun getItemCount(): Int {
        return  listData.size
    }
}