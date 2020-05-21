package com.chentir.favqs.data.entities

import com.google.gson.annotations.SerializedName

data class UserIdentifier(@SerializedName("user") val user: User)

data class User(
  @SerializedName("login") val login: String,
  @SerializedName("password") val password: String
)