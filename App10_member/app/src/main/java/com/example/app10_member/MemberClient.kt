package com.example.app10_member

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MemberClient {
    val retrofit:MemberInterface = Retrofit.Builder()
        .baseUrl("http://10.100.105.150:8899/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MemberInterface::class.java)
}