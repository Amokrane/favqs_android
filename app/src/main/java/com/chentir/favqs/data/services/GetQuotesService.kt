package com.chentir.favqs.data.services

import com.chentir.favqs.data.entities.Quotes
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GetQuotesService {
  @Headers(
      "Content-Type: application/json",
      "Authorization: Token token=3076049eda9ba452981badda30fe2d47"
  )
  @GET("api/quotes/")
  suspend fun getQuotes(
    @Query("page") page: Int, @Query("filter") filter: String, @Query(
        "type"
    ) type: String
  ): Quotes
}