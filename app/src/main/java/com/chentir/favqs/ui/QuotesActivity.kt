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
import com.chentir.favqs.ui.viewmodels.QuotesViewModel
import com.chentir.favqs.ui.viewmodels.factories.QuotesViewModelFactory

class QuotesActivity : AppCompatActivity() {
  private lateinit var binding: ActivityQuotesBinding
  private lateinit var viewModel: QuotesViewModel
  private lateinit var viewModeFactory: QuotesViewModelFactory
  private lateinit var quotesAdapter: QuotesAdapter

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
    viewModeFactory =
      QuotesViewModelFactory(DependencyProvider.provideQuotesRepository(applicationContext))

    viewModel = viewModeFactory.create(QuotesViewModel::class.java)

    val username = intent.getStringExtra(EXTRA_USERNAME)
    val liveData = viewModel.getQuotes(page = 1, username = username)
    liveData.observe(this, Observer {
      when (it) {
        is Lce.Error -> {
          Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
              .show()
        }
        is Lce.Success -> {
          val fetchNextPage: (Int) -> Unit = { nextPage ->
            val liveDataNextPage = viewModel.getQuotes(page = nextPage, username = username)
            liveDataNextPage.observe(this, Observer { nextQuotesResource ->
              nextQuotesResource.data?.let { nextQuotes ->
                quotesAdapter.addQuotes(nextQuotes.quoteEntities)
                if (nextQuotes.lastPage) {
                  quotesAdapter.stopPaging()
                }
                quotesAdapter.notifyDataSetChanged()
              }
            })
          }

          quotesAdapter = QuotesAdapter(
              it.data.quoteEntities.toMutableList(), prefetchDistance = 1,
              fetchNextPage = fetchNextPage
          )

          binding.listQuotes.adapter = quotesAdapter
        }
      }
    })
  }
}