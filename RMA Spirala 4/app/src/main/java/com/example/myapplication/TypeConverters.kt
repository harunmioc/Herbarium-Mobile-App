package com.example.myapplication

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MedicinskaKoristTypeConverter {
    @TypeConverter
    fun fromMedicinskaKoristList(value: List<MedicinskaKorist>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMedicinskaKoristList(value: String): List<MedicinskaKorist>? {
        val gson = Gson()
        val type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.fromJson(value, type)
    }
}

class ProfilOkusaTypeConverter {
    @TypeConverter
    fun fromProfilOkusa(value: ProfilOkusaBiljke?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProfilOkusa(value: String): ProfilOkusaBiljke? {
        val gson = Gson()
        return gson.fromJson(value, ProfilOkusaBiljke::class.java)
    }
}

class StringListTypeConverter {
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}

class KlimatskiTipTypeConverter {
    @TypeConverter
    fun fromKlimatskiTipList(value: List<KlimatskiTip>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toKlimatskiTipList(value: String): List<KlimatskiTip>? {
        val gson = Gson()
        val type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.fromJson(value, type)
    }
}

class ZemljisteTypeConverter {
    @TypeConverter
    fun fromZemljisteList(value: List<Zemljiste>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toZemljisteList(value: String): List<Zemljiste>? {
        val gson = Gson()
        val type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.fromJson(value, type)
    }
}
