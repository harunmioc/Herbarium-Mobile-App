package com.example.myapplication

import android.graphics.Bitmap;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(
    foreignKeys = [ForeignKey(entity = Biljka::class,
        parentColumns = ["id"],
        childColumns = ["idBiljke"],
        onDelete = ForeignKey.CASCADE)]
)

@TypeConverters(
    BitmapConverter::class
)

data class BiljkaBitmap(
    val idBiljke: Long,
    val bitmap:Bitmap,
    @PrimaryKey(autoGenerate = true) val id: Long ?= null
)