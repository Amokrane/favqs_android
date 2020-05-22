package com.chentir.favqs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserSessionEntity::class], version = 1)
abstract class FavqsDatabase : RoomDatabase() {
  abstract fun userSessionDao(): UserSessionDao
}