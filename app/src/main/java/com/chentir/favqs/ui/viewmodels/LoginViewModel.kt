package com.chentir.favqs.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chentir.favqs.data.entities.UserSession
import com.chentir.favqs.data.repositories.AuthenticationRepository
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.data.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
  fun createSession(
    username: String,
    password: String
  ): LiveData<Lce<UserSession>> {
    val userLiveData = MutableLiveData<Lce<UserSession>>()

    viewModelScope.launch(Dispatchers.IO) {
      val resource = authenticationRepository.authenticate(username, password)
      when (resource.status) {
        Status.SUCCESS -> userLiveData.postValue(Lce.Success(resource.data!!))
        Status.ERROR -> userLiveData.postValue(Lce.Error(resource.message!!))
      }
    }
    return userLiveData
  }
}