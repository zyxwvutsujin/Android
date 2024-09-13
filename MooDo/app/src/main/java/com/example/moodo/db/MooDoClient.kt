package com.example.moodo.db

import com.example.moodo.Holiday.HolidayService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

// 각자 포트번호로 바꾸셔야 합니다
object MooDoClient {

    val retrofit:MooDoInterface = Retrofit.Builder()
        .baseUrl("http://10.100.105.150:8899/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MooDoInterface::class.java)

    val holidayService: HolidayService = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/")  // 공휴일 API baseUrl
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()
        .create(HolidayService::class.java)

}