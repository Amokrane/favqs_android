package com.chentir.favqs.data.entities

import com.google.gson.annotations.SerializedName

data class User(
  @SerializedName("User-Token") val userToken: String,
  @SerializedName("login") val login: String,
  @SerializedName("email") val email: String
)