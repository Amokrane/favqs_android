package com.chentir.favqs.data.services

import com.chentir.favqs.data.entities.UserIdentifier
import com.chentir.favqs.data.entities.UserSessionEntity
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CreateSessionService {
  @Headers(
      "Content-Type: application/json",
      "Authorization: Token token=3076049eda9ba452981badda30fe2d47"
  )
  @POST("api/session")
  suspend fun session(@Body userIdentifier: UserIdentifier): UserSessionEntity
}