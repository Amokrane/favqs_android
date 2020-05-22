package com.chentir.favqs.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chentir.favqs.data.repositories.UserRepository
import com.chentir.favqs.ui.viewmodels.ProfileViewModel
import com.chentir.favqs.ui.viewmodels.SplashViewModel

class ProfileViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
      return ProfileViewModel(userRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}