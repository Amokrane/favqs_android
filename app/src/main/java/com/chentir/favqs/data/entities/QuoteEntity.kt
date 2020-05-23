package com.chentir.favqs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class QuoteEntity(
  @ColumnInfo(name = "body") @SerializedName("body") val body: String,
  @ColumnInfo(name = "author") @SerializedName("author") val author: String,
  @ColumnInfo(name = "url") @SerializedName("url") val url: String,
  @ColumnInfo(name = "upvotes_count") @SerializedName("upvotes_count") val upvotesCount: Int,
  @ColumnInfo(name = "downvotes_count") @SerializedName("downvotes_count") val downvotesCount: Int,
  @ColumnInfo(name = "page") val page: Int = 1,
  @ColumnInfo(name = "last_page") val lastPage: Boolean = false,
  @ColumnInfo(name = "username") val username: String
) {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Long = 0
}