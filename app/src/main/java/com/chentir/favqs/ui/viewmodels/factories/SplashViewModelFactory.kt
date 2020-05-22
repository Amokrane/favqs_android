package com.chentir.favqs.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.ui.viewmodels.SplashViewModel

class SplashViewModelFactory(private val userSessionDao: UserSessionDao) :
    ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
      return SplashViewModel(userSessionDao) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }

}