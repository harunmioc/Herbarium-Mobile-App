package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class BiljkaData(
    @SerializedName("id") val id: Long,
    @SerializedName("common_name") val commonName: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("year") val year: Int,
    @SerializedName("bibliography") val bibliography: String,
    @SerializedName("author") val author: String,
    @SerializedName("status") val status: String,
    @SerializedName("rank") val rank: String,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("genus_id") val genusID: Long,
    @SerializedName("image_url") val imageURL: String,
    @SerializedName("synonyms") val synonyms: List<String>,
    @SerializedName("genus") val name: String,
    @SerializedName("family") val family: String,
    @SerializedName("links") val links: Map<String, String>,
    @SerializedName("edible") val edible:Boolean,
    )

data class Family(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("common_name") val commonName: String?,
    )

data class Specification(
    @SerializedName("toxicity") val toxicity: String,
)

data class Growth(
    @SerializedName("light") val light: Int,
    @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int,
    @SerializedName("soil_texture") val soilTexture: Int?,
    )