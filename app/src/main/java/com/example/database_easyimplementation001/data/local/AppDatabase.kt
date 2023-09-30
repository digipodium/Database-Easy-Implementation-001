package com.example.database_easyimplementation001.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class AppDatabase:RoomDatabase() {
    abstract val dao: ContactDao
}