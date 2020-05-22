package com.chentir.favqs.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chentir.favqs.data.entities.UserEntity
import com.chentir.favqs.data.repositories.UserRepository
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.data.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
  fun fetchUser(): LiveData<Lce<UserEntity>> {
    val liveData = MutableLiveData<Lce<UserEntity>>()
    viewModelScope.launch(Dispatchers.IO) {
      val resource = userRepository.getUser()
      when (resource.status) {
        Status.SUCCESS -> liveData.postValue(Lce.Success(resource.data!!))
        Status.ERROR -> liveData.postValue(Lce.Error(resource.message!!))
      }
    }
    return liveData
  }
}