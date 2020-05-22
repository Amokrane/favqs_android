package com.chentir.favqs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class UserSessionEntity(
  @ColumnInfo(name = "User-Token")
  @SerializedName("User-Token")
  val userToken: String,
  @ColumnInfo(name = "login")
  @SerializedName("login")
  val login: String,
  @ColumnInfo(name = "email")
  @SerializedName("email")
  val email: String
) {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Long = 0
}