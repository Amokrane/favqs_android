package com.chentir.favqs.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.chentir.favqs.data.entities.QuoteEntity

@Dao
abstract class QuotesDao {
  @Query("SELECT * FROM QuoteEntity WHERE page = :page")
  abstract suspend fun getQuotes(page: Int): List<QuoteEntity>

  @Transaction
  open suspend fun insert(
    quoteEntities: List<QuoteEntity>,
    page: Int
  ) {
    deleteAllQuoteEntitiesInPage(page)
    quoteEntities.forEach {
      doInsert(it)
    }
  }

  @Insert
  protected abstract suspend fun doInsert(quoteEntity: QuoteEntity)

  @Query("DELETE FROM QuoteEntity WHERE page = :page")
  protected abstract suspend fun deleteAllQuoteEntitiesInPage(page: Int)
}