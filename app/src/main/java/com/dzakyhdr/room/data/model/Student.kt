package com.dzakyhdr.room.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "name")
    var judul: String = "",
    @ColumnInfo(name = "email")
    var catatan: String = ""
): Parcelable
