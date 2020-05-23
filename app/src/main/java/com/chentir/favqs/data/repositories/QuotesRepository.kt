package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.Quotes
import com.chentir.favqs.data.local.QuotesDao
import com.chentir.favqs.data.services.GetQuotesService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler

class QuotesRepository(
  private val getQuotesService: GetQuotesService,
  private val quotesDao: QuotesDao
) {
  suspend fun getQuotes(page: Int): Resource<Quotes> {
    return try {
      var quotes = getQuotesService.getQuotes(page)
      val quoteEntities = quotes.quoteEntities.map { quoteEntity ->
        quoteEntity.copy(
            page = quotes.page, lastPage = quotes.lastPage
        )
      }
      quotes = quotes.copy(quoteEntities = quoteEntities)
      quotesDao.insert(quotes.quoteEntities, page)
      ResponseHandler.handleSuccess(quotes)
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }
}