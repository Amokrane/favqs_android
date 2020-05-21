package com.chentir.favqs.data.services

import com.chentir.favqs.data.entities.User
import com.chentir.favqs.data.entities.UserIdentifier
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateSessionService {
  @POST("api/session")
  suspend fun session(@Body userIdentifier: UserIdentifier): User
}