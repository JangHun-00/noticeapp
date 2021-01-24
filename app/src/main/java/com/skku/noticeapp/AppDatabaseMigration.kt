package com.skku.noticeapp

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
                CREATE TABLE new_notice (
                    no TEXT,
                    name TEXT PRIMARY KEY NOT NULL,
                    link TEXT,
                    date TEXT,
                    read TEXT,
                    is_new TEXT
                )
                """.trimIndent())
        database.execSQL("""
                INSERT INTO new_notice (no, name, link, date, read, is_new)
                SELECT no, name, link, date, read, is_new FROM notice
                """.trimIndent())
        database.execSQL("DROP TABLE notice")
        database.execSQL("ALTER TABLE new_notice RENAME TO notice")
    }
}