package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class TrefleIDResponse(
    @SerializedName("data") val data: BiljkaData2
)
