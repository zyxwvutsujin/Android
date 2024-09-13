package com.example.moodo.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodo.databinding.ItemListMonthBinding
import com.example.moodo.db.MooDoClient
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MonthAdapter(val userId: String, val userAge: String) : RecyclerView.Adapter<MonthAdapter.MonthHolder>() {
    private val center = Int.MAX_VALUE / 2
    private var calendar = Calendar.getInstance()
    private val today = calendar.time

    var holidayDates: Set<String> = emptySet()
    private var dayAdapter: DayAdapter? = null

    interface OnDaySelectedListener {
        fun onDaySelected(date: String)
    }

    var onDaySelectedListener: OnDaySelectedListener? = null

    inner class MonthHolder(val binding: ItemListMonthBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        return MonthHolder(ItemListMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)

        holder.binding.itemMonthTxt.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"

        val tempMonth = calendar.get(Calendar.MONTH)

        val dayList: MutableList<Date> = MutableList(5 * 7) { Date() }
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.add(Calendar.DAY_OF_MONTH, -(tempCalendar.get(Calendar.DAY_OF_WEEK) - 1))

        var todayPosition = -1
        val birthDayFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        val dayFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val userBirthday = birthDayFormat.format(SimpleDateFormat("yyyy/MM/dd").parse(userAge))

        val emojiList: MutableList<String> = MutableList(5 * 7) { "none" }
        val tdCntList: MutableList<Int> = MutableList(5 * 7) { 0 }

        var apiCnt = 0
        val totalRequest = 5 * 7

        fun checkAllDataLoaded() {
            if (apiCnt == totalRequest) {
                dayAdapter = DayAdapter(tempMonth, dayList, todayPosition, emojiList, tdCntList, userId).apply {
                    clickItemDayListener = object : DayAdapter.ClickItemDayListener {
                        override fun clickItemDay(position: Int) {
                            val selectedDay = dayList[position]
                            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDay)
                            onDaySelectedListener?.onDaySelected(formattedDate)
                        }
                    }
                }

                holder.binding.itemMonthDayList.layoutManager = GridLayoutManager(holder.binding.root.context, 7)
                holder.binding.itemMonthDayList.adapter = dayAdapter
            }
        }

        for (i in 0 until 5) {
            for (k in 0 until 7) {
                dayList[i * 7 + k] = tempCalendar.time
                val formattedDay = dayFormatter.format(tempCalendar.time)

                if (holidayDates.contains(formattedDay)) {
                    emojiList[i * 7 + k] = "holiday"
                }

                if (tempCalendar.get(Calendar.MONTH) == tempMonth) {
                    val formattedBirth = birthDayFormat.format(tempCalendar.time)
                    if (userBirthday == formattedBirth) {
                        // User Birthday Logic
                    } else {
                        MooDoClient.retrofit.getMdMode(userId, formattedDay).enqueue(object : retrofit2.Callback<Int> {
                            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                                if (response.isSuccessful) {
                                    when (response.body()) {
                                        1 -> emojiList[i * 7 + k] = "angry"
                                        2 -> emojiList[i * 7 + k] = "sad"
                                        3 -> emojiList[i * 7 + k] = "meh"
                                        4 -> emojiList[i * 7 + k] = "s_happy"
                                        5 -> emojiList[i * 7 + k] = "happy"
                                    }
                                } else {
                                    emojiList[i * 7 + k] = "none"
                                }
                                apiCnt++
                                checkAllDataLoaded()
                            }

                            override fun onFailure(call: Call<Int>, t: Throwable) {
                                emojiList[i * 7 + k] = "none"
                                apiCnt++
                                checkAllDataLoaded()
                            }
                        })
                    }

                    MooDoClient.retrofit.getTodoCountForDay(userId, formattedDay).enqueue(object : retrofit2.Callback<Int> {
                        override fun onResponse(call: Call<Int>, response: Response<Int>) {
                            tdCntList[i * 7 + k] = response.body() ?: 0
                            apiCnt++
                            checkAllDataLoaded()
                        }

                        override fun onFailure(call: Call<Int>, t: Throwable) {
                            tdCntList[i * 7 + k] = 0
                            apiCnt++
                            checkAllDataLoaded()
                        }
                    })
                } else {
                    apiCnt++
                    checkAllDataLoaded()
                }

                if (SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(tempCalendar.time) == SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today)) {
                    todayPosition = i * 7 + k
                }

                tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
    }
}
