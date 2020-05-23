package com.chentir.favqs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chentir.favqs.R
import com.chentir.favqs.data.entities.QuoteEntity

class FavoriteQuotesAdapter(
  private val quotes: MutableList<QuoteEntity>,
  private val prefetchDistance: Int,
  private val fetchNextPage: (Int) -> Unit
) :
    ListAdapter<QuoteEntity, FavoriteQuotesAdapter.QuotesViewHolder>(
        object : DiffUtil.ItemCallback<QuoteEntity>() {
          override fun areItemsTheSame(
            oldItem: QuoteEntity,
            newItem: QuoteEntity
          ): Boolean =
            oldItem == newItem

          override fun areContentsTheSame(
            oldItem: QuoteEntity,
            newItem: QuoteEntity
          ): Boolean =
            oldItem == newItem
        }
    ) {

  private var currentPage = 1
  private var stopPaging = false

  class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val quoteText: TextView = itemView.findViewById(R.id.quote_text)
    val quoteAuthor: TextView = itemView.findViewById(R.id.quote_author)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): QuotesViewHolder {
    val itemView = LayoutInflater.from(parent.context)
        .inflate(R.layout.quote_item, parent, false)

    return QuotesViewHolder(itemView)

  }

  override fun onBindViewHolder(
    holder: QuotesViewHolder,
    position: Int
  ) {
    if (position == itemCount - prefetchDistance - 1 && !stopPaging) {
      fetchNextPage(++currentPage)
    }

    val quote = quotes[position]
    holder.quoteText.text = quote.body
    holder.quoteAuthor.text = quote.author
  }

  override fun getItemCount(): Int = quotes.size

  fun addQuotes(quotes: List<QuoteEntity>) {
    this.quotes.addAll(quotes)
  }

  fun stopPaging() {
    this.stopPaging = true
  }

}