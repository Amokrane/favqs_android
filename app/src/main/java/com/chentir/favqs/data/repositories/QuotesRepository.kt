package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.QuoteEntity
import com.chentir.favqs.data.services.GetQuotesService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler

class QuotesRepository(private val getQuotesService: GetQuotesService) {

  suspend fun getQuotes(page: Int): Resource<List<QuoteEntity>> {
    return try {
      val quotes = getQuotesService.getQuotes(page)
      ResponseHandler.handleSuccess(quotes.quotes)
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }
}