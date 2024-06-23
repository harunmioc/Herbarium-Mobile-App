package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class TrefleResponse(
    @SerializedName("data") val data: List<BiljkaData>
)