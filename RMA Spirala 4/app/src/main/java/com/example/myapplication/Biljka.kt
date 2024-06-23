package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity
@TypeConverters(
    MedicinskaKoristTypeConverter::class,
    ProfilOkusaTypeConverter::class,
    StringListTypeConverter::class,
    KlimatskiTipTypeConverter::class,
    ZemljisteTypeConverter::class
)


data class Biljka(
    val naziv : String,
    val family : String,
    val medicinskoUpozorenje : String,
    val medicinskeKoristi : List<MedicinskaKorist>,
    val profilOkusa : ProfilOkusaBiljke?,
    val jela : List<String>,
    val klimatskiTipovi : List<KlimatskiTip>,
    val zemljisniTipovi  : List<Zemljiste>,

    var onlineChecked: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Long? = null
)