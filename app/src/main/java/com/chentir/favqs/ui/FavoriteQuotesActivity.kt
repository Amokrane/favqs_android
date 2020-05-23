package com.chentir.favqs.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chentir.favqs.DependencyProvider
import com.chentir.favqs.R
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.databinding.ActivityQuotesBinding
import com.chentir.favqs.ui.viewmodels.FavoriteQuotesViewModel
import com.chentir.favqs.ui.viewmodels.factories.FavoriteQuotesViewModelFactory

class FavoriteQuotesActivity : AppCompatActivity() {
  private lateinit var binding: ActivityQuotesBinding
  private lateinit var viewModelFavorite: FavoriteQuotesViewModel
  private lateinit var viewModeFactoryFavorite: FavoriteQuotesViewModelFactory
  private lateinit var favoriteQuotesAdapter: FavoriteQuotesAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    binding = ActivityQuotesBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    val layoutManager = LinearLayoutManager(view.context)
    val divideItemDecoration =
      DividerItemDecoration(binding.listQuotes.context, layoutManager.orientation)

    binding.listQuotes.addItemDecoration(divideItemDecoration)
    viewModeFactoryFavorite =
      FavoriteQuotesViewModelFactory(DependencyProvider.provideQuotesRepository(applicationContext))

    viewModelFavorite = viewModeFactoryFavorite.create(FavoriteQuotesViewModel::class.java)

    val username = intent.getStringExtra(EXTRA_USERNAME)
    val liveData = viewModelFavorite.getQuotes(page = 1, username = username)
    liveData.observe(this, Observer {
      when (it) {
        is Lce.Error -> {
          Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
              .show()
        }
        is Lce.Success -> {
          val fetchNextPage: (Int) -> Unit = { nextPage ->
            val liveDataNextPage = viewModelFavorite.getQuotes(page = nextPage, username = username)
            liveDataNextPage.observe(this, Observer { nextQuotesResource ->
              nextQuotesResource.data?.let { nextQuotes ->
                favoriteQuotesAdapter.addQuotes(nextQuotes.quoteEntities)
                if (nextQuotes.lastPage) {
                  favoriteQuotesAdapter.stopPaging()
                }
                favoriteQuotesAdapter.notifyDataSetChanged()
              }
            })
          }

          favoriteQuotesAdapter = FavoriteQuotesAdapter(
              it.data.quoteEntities.toMutableList(), prefetchDistance = 1,
              fetchNextPage = fetchNextPage
          )

          binding.listQuotes.adapter = favoriteQuotesAdapter
        }
      }
    })
  }
}