package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class mainSpecies(@SerializedName("id") val id: Long,
                       @SerializedName("edible") val edible:Boolean,
                       @SerializedName("specifications") val specifications: Specification,
                       @SerializedName("growth") val growth: Growth,
                       @SerializedName("family") val family: String,
)
