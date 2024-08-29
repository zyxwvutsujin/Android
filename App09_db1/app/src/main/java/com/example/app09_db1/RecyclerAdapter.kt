package com.example.app09_db1

import SqliteHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app09_db1.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter
    : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var listData = mutableListOf<Memo>()
    var helper:SqliteHelper? = null

    interface OnItemClickLister {
        fun onItemClick(pos:Int)
    }
    var onItemClickLister:OnItemClickLister? = null

    inner class Holder(val binding: ItemRecyclerBinding)
        :RecyclerView.ViewHolder(binding.root) {
        var mMemo:Memo? = null
        init {
            // 삭제버튼
            binding.buttonDelete.setOnClickListener {
                mMemo?.let { memo ->
                    // db에서 삭제
                    helper?.deleteMemo(memo)
                    /// mutableListOf<Memo>에서 삭제
                    listData.remove(memo)
                    notifyDataSetChanged()
                } ?: run {
                    Log.e("RecyclerAdapter", "Memo is null")
                }
            }


            // 리사이클러 클릭 시
            itemView.setOnClickListener {
                onItemClickLister?.onItemClick(adapterPosition)
            }
        }
        fun setMemo(memo: Memo) {
            binding.textNo.text = "${memo.num}"
            binding.textContent.text = memo.content
            //            binding.textDateTime.text = memo.datetime.toString()
//            날짜포맷 SImpleDateFormat
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.textDateTime.text = "${sdf.format(memo.datetime)}"
            mMemo = memo // mMemo를 memo로 설정
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}