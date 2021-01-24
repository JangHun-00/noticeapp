package com.skku.noticeapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Notice(@ColumnInfo(name="no") val no: Int = 0,
             @PrimaryKey val name: String = "",
             @ColumnInfo(name="link") val link: String = "",
             @ColumnInfo(name="date") val date: String = "",
             @ColumnInfo(name="read") val read: Int = 0,
             @ColumnInfo(name="is_new") val is_new: String = "")