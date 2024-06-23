package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAdapter {
    val retrofit : Api = Retrofit.Builder()
        .baseUrl("https://trefle.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}