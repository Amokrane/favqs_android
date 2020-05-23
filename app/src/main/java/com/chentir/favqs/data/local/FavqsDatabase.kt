package com.chentir.favqs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chentir.favqs.data.entities.QuoteEntity
import com.chentir.favqs.data.entities.UserEntity
import com.chentir.favqs.data.entities.UserSessionEntity

@Database(
    entities = [UserSessionEntity::class, QuoteEntity::class, UserEntity::class], version = 1
)
abstract class FavqsDatabase : RoomDatabase() {
  abstract fun userSessionDao(): UserSessionDao
  abstract fun userDao(): UserDao
  abstract fun quotesDao(): QuotesDao
}