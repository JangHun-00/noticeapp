package com.skku.noticeapp

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Notice::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
}