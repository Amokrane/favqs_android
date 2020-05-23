package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.Quotes
import com.chentir.favqs.data.services.GetQuotesService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler

class QuotesRepository(private val getQuotesService: GetQuotesService) {
  suspend fun getQuotes(page: Int): Resource<Quotes> {
    return try {
      var quotes = getQuotesService.getQuotes(page)
      quotes.copy(quotes = quotes.quotes.map { quoteEntity ->
        quoteEntity.copy(
            page = quotes.page, lastPage = quotes.lastPage
        )
      })
      ResponseHandler.handleSuccess(quotes)
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }
}