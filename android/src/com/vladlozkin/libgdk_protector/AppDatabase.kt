package com.vladlozkin.libgdk_protector

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Score::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ScoreDao
}