package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.Quotes
import com.chentir.favqs.data.local.QuotesDao
import com.chentir.favqs.data.services.GetQuotesService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler
import com.chentir.favqs.utils.ConnectivityHelper

class QuotesRepository(
  private val getQuotesService: GetQuotesService,
  private val quotesDao: QuotesDao,
  private val connectivityHelper: ConnectivityHelper
) {
  suspend fun getQuotes(page: Int, username: String): Resource<Quotes> {
    var quotes = Quotes(page = 0, lastPage = true, quoteEntities = listOf())
    return try {
      quotes = if (connectivityHelper.isConnected()) {
        getQuotesFromRemote(page, username)  ?: quotes
      } else {
        getQuotesFromLocalDb(page) ?: quotes
      }
      ResponseHandler.handleSuccess(quotes)
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }

  private suspend fun getQuotesFromRemote(
    page: Int,
    username: String
  ): Quotes? {
    var quotes = getQuotesService.getQuotes(page = page, filter = username, type = "user")
    val quoteEntities = quotes.quoteEntities.map { quoteEntity ->
      quoteEntity.copy(
          page = quotes.page, lastPage = quotes.lastPage
      )
    }
    quotes = quotes.copy(quoteEntities = quoteEntities)
    quotesDao.insert(quotes.quoteEntities, page)
    return quotes
  }

  private suspend fun getQuotesFromLocalDb(page: Int): Quotes? {
    val quotesEntities = quotesDao.getQuotes(page)
    if (quotesEntities != null && quotesEntities.isNotEmpty()) {
      return Quotes(
          quoteEntities = quotesEntities, page = page, lastPage = quotesEntities[0].lastPage
      )
    }
    return null
  }
}