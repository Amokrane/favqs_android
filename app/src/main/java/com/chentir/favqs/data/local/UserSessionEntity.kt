package com.chentir.favqs.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserSessionEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  @ColumnInfo(name = "User-Token")
  val userToken: String,
  @ColumnInfo(name = "login")
  val login: String,
  @ColumnInfo(name = "email")
  val email: String
)