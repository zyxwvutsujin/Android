package com.example.moodo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodo.Holiday.HolidayItem
import com.example.moodo.R
import com.example.moodo.databinding.ItemTodoListBinding
import com.example.moodo.db.MooDoToDo
import java.text.SimpleDateFormat
import java.util.Locale

class ToDoAdapter() :RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {
    var todoList = mutableListOf<MooDoToDo>()
    var holidayList = mutableListOf<HolidayItem>()

    interface OnItemClickLister {
        fun onItemClick(pos:Int)
    }
    var onItemClickLister:OnItemClickLister? = null

    inner class ToDoHolder(val binding:ItemTodoListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClickLister?.onItemClick(adapterPosition)
            }
        }
    }

    // 추가
    fun addItem(todoItem:MooDoToDo) {
        todoList.add(todoItem)
        notifyDataSetChanged()
    }

    // 공휴일 추가
    fun addHoliday(holidayItem: HolidayItem) {
        holidayList.add(holidayItem)
        notifyDataSetChanged() // 공휴일 추가 후 어댑터 갱신
    }

    // 수정
    fun updateItem(pos: Int, toDo: MooDoToDo) {
        todoList.set(pos, toDo)
        notifyDataSetChanged()
    }
    // 삭제
    fun removeItem(pos:Int) {
        todoList.removeAt(pos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        return ToDoHolder(ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return holidayList.size + todoList.size
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        if (position < holidayList.size) {
            // 공휴일 처리
            val holiday = holidayList[position]
            holder.binding.itemToDo.text = holiday.dateName  // 공휴일 이름 표시
            holder.binding.startToDo.text = ""  // 공휴일은 시작 시간이 없음
            holder.binding.endToDo.text = ""    // 공휴일은 종료 시간이 없음
            holder.binding.tdListBox.setBackgroundResource(R.drawable.td_red_box) // 공휴일 박스 색상 설정
        } else {
            // 할 일 처리
            val todoPosition = position - holidayList.size  // 공휴일 이후의 할 일 인덱스 계산
            val todoItem = todoList[todoPosition]

            holder.binding.itemToDo.text = todoItem.tdList

            // 시작 날짜와 종료 날짜 처리
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("M/d a hh:mm", Locale.getDefault())

            val startDate = inputFormat.parse(todoItem.startDate)
            val formattedStartDate = startDate?.let { outputFormat.format(it) } ?: ""

            val endDate = inputFormat.parse(todoItem.endDate)
            val formattedEndDate = endDate?.let { outputFormat.format(it) } ?: ""

            holder.binding.startToDo.text = formattedStartDate
            holder.binding.endToDo.text = formattedEndDate

            // 할 일의 색깔에 따른 박스 스타일 적용
            when(todoItem.color) {
                "red" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_red_box)
                "blue" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_blue_box)
                "orange" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_orange_box)
                "green" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_green_box)
                "yellow" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_yellow_box)
            }

            // 완료 여부 체크 아이콘 설정
            when(todoItem.tdCheck) {
                "N" -> holder.binding.tdChecked.setImageResource(R.drawable.td_non_check)
                "Y" -> holder.binding.tdChecked.setImageResource(R.drawable.td_check)
            }
        }
    }
}