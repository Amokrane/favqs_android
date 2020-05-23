package com.chentir.favqs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.chentir.favqs.data.entities.QuoteEntity

@Dao
abstract class QuotesDao {
  @Query("SELECT * FROM QuoteEntity WHERE page = :page AND username = :username")
  abstract suspend fun getQuotes(
    page: Int,
    username: String
  ): List<QuoteEntity>

  @Transaction
  open suspend fun insert(
    quoteEntities: List<QuoteEntity>,
    page: Int,
    username: String
  ) {
    deleteAllQuoteEntitiesInPage(page, username)
    quoteEntities.forEach {
      doInsert(it)
    }
  }

  @Insert
  protected abstract suspend fun doInsert(quoteEntity: QuoteEntity)

  @Query("DELETE FROM QuoteEntity WHERE page = :page AND username = :username")
  protected abstract suspend fun deleteAllQuoteEntitiesInPage(page: Int, username: String)
}