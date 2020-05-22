package com.chentir.favqs.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.data.entities.UserSessionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val userSessionDao: UserSessionDao) : ViewModel() {
  fun checkUserSession(): LiveData<UserSessionEntity?> {
    val liveData = MutableLiveData<UserSessionEntity?>()
    viewModelScope.launch(Dispatchers.IO) {
      liveData.postValue(userSessionDao.getLastUserSession())
    }
    return liveData
  }
}