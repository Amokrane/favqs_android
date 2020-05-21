package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.User
import com.chentir.favqs.data.entities.UserSession
import com.chentir.favqs.data.entities.UserIdentifier
import com.chentir.favqs.data.services.CreateSessionService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler
import java.lang.Exception

class AuthenticationRepository(private val createSessionService: CreateSessionService) {
  suspend fun authenticate(username: String, password: String): Resource<UserSession> {
    return try {
      val userIdentifier = UserIdentifier(User(username, password))
      ResponseHandler.handleSuccess(createSessionService.session(userIdentifier))
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }
}