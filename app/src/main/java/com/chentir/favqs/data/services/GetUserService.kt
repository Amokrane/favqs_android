package com.chentir.favqs.data.services

import com.chentir.favqs.data.entities.UserEntity
import retrofit2.http.GET
import retrofit2.http.Header

interface GetUserService {
  @GET("api/users/")
  suspend fun getUser(
    @Header("Content-Type") contentType: String,
    @Header("Authorization") authorization: String,
    @Header("User-Token") userToken: String
  ): UserEntity
}