package com.chentir.favqs.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chentir.favqs.data.repositories.QuotesRepository
import com.chentir.favqs.ui.viewmodels.QuotesViewModel

class QuotesViewModelFactory(private val quotesRepository: QuotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuotesViewModel::class.java)) {
            return QuotesViewModel(quotesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}