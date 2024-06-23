package com.example.myapplication

import android.graphics.Bitmap
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.myapplication.BuildConfig
import retrofit2.http.Path

interface Api {

    @GET("api/v1/plants/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("token") token: String = BuildConfig.API_KEY
    ): Response<TrefleResponse>

    @GET("api/v1/plants/search")
    suspend fun searchPlantsByColor(
        @Query("filter[flower_color]") flowerColor: String,
        @Query("q") query: String,
        @Query("token") token: String = BuildConfig.API_KEY
    ): Response<TrefleResponse>

    @GET("api/v1/plants/{id}")
    suspend fun fixData(
        @Query("q") query: String,
        @Query("token") token: String = BuildConfig.API_KEY
    ): Response<TrefleResponse>

    @GET("api/v1/plants/{id}")
    suspend fun getPlantById(
        @Path("id") id: Long,
        @Query("token") token: String = BuildConfig.API_KEY
    ): Response<TrefleIDResponse>
}