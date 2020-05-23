package com.chentir.favqs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.chentir.favqs.data.entities.UserEntity

@Dao
abstract class UserDao {
  @Query("SELECT * FROM UserEntity LIMIT 1")
  abstract suspend fun get(): UserEntity?

  @Transaction
  open suspend fun insert(userEntity: UserEntity) {
    delete()
    doInsert(userEntity)
  }

  @Insert protected abstract suspend fun doInsert(userEntity: UserEntity)

  @Query("DELETE FROM UserEntity")
  protected abstract suspend fun delete()
}