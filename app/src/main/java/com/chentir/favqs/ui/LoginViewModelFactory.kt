package com.chentir.favqs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chentir.favqs.data.repositories.AuthenticationRepository
import com.chentir.favqs.ui.viewmodels.LoginViewModel

class LoginViewModelFactory(private val authenticationRepository: AuthenticationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authenticationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}