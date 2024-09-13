package com.example.moodo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodo.Holiday.HolidayItem
import com.example.moodo.R
import com.example.moodo.databinding.ItemCalendarTdlistBinding
import com.example.moodo.db.MooDoToDo
import java.text.SimpleDateFormat
import java.util.Locale

class CalendarToDoAdapter() :RecyclerView.Adapter<CalendarToDoAdapter.ToDoHolder>() {
    var todoList = mutableListOf<MooDoToDo>()
    var holidayList = mutableListOf<HolidayItem>()

    interface OnItemClickLister {
        fun onItemClick(pos:Int)
    }
    var onItemClickLister:OnItemClickLister? = null

    inner class ToDoHolder(val binding:ItemCalendarTdlistBinding) : RecyclerView.ViewHolder(binding.root) {
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
        return ToDoHolder(ItemCalendarTdlistBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return holidayList.size + todoList.size
    }

    // 공휴일과 할 일을 구분하여 UI에 표시
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        if (position < holidayList.size) {
            // 공휴일 처리
            val holiday = holidayList[position]
            holder.binding.itemToDo.text = holiday.dateName
            holder.binding.startToDo.text = ""  // 공휴일은 시작 시간이 없음
            holder.binding.endToDo.text = ""    // 공휴일은 종료 시간이 없음
            holder.binding.tdListBox.setBackgroundResource(R.drawable.td_yellow_box) // 공휴일 박스 스타일
        } else {
            // 할 일 처리
            val todoPosition = position - holidayList.size
            val todoItem = todoList[todoPosition]

            holder.binding.itemToDo.text = todoItem.tdList

            // 시작 시간과 종료 시간 포맷팅
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("M/d a hh:mm", Locale.getDefault())

            val startDate = inputFormat.parse(todoItem.startDate)
            val formattedStartDate = startDate?.let { outputFormat.format(it) } ?: ""

            val endDate = inputFormat.parse(todoItem.endDate)
            val formattedEndDate = endDate?.let { outputFormat.format(it) } ?: ""

            holder.binding.startToDo.text = formattedStartDate
            holder.binding.endToDo.text = formattedEndDate

            // 할 일 색상에 따른 박스 스타일
            when(todoItem.color) {
                "red" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_red_box)
                "blue" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_blue_box)
                "orange" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_orange_box)
                "green" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_green_box)
                "yellow" -> holder.binding.tdListBox.setBackgroundResource(R.drawable.td_yellow_box)
            }
        }
    }
}