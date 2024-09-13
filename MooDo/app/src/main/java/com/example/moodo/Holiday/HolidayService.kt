package com.example.moodo.Holiday

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayService {
    @GET("getRestDeInfo")
    fun getHolidays(
        @Query("serviceKey") serviceKey: String,
        @Query("solYear") year: Int,
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("pageNo") pageNo: Int = 1
    ): Call<HolidayResponse>
}
