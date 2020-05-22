package com.chentir.favqs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class UserEntity(
  @ColumnInfo(name = "login") val login: String,
  @ColumnInfo(name = "pic_url") @SerializedName("pic_url") val picUrl: String,
  @ColumnInfo(name = "public_favorites_count") @SerializedName(
      "public_favorites_count"
  ) val quotesCount: Int
) {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Long = 0
}