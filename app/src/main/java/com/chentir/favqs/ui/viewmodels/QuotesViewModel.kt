package com.chentir.favqs.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chentir.favqs.data.entities.QuoteEntity
import com.chentir.favqs.data.repositories.QuotesRepository
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.data.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesViewModel(private val quotesRepository: QuotesRepository) : ViewModel() {
    fun getQuotes(page: Int): LiveData<Lce<List<QuoteEntity>>> {
        val liveData = MutableLiveData<Lce<List<QuoteEntity>>>()
        viewModelScope.launch(Dispatchers.IO) {
            val resource = quotesRepository.getQuotes(page)
            when (resource.status) {
                Status.SUCCESS -> liveData.postValue(Lce.Success(resource.data!!))
                Status.ERROR -> liveData.postValue(Lce.Error(resource.message!!))
            }
        }
        return liveData
    }
}