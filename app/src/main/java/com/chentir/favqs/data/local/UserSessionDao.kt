package com.chentir.favqs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class UserSessionDao {
  @Query("SELECT * FROM UserSessionEntity LIMIT 1")
  abstract suspend fun getLastUserSession(): UserSessionEntity

  @Transaction
  open suspend fun insert(userSessionEntity: UserSessionEntity) {
    deleteAllUserSessions()
    doInsert(userSessionEntity)
  }

  @Insert
  protected abstract suspend fun doInsert(userSessionEntity: UserSessionEntity)

  @Query("DELETE FROM UserSessionEntity")
  protected abstract suspend fun deleteAllUserSessions()
}