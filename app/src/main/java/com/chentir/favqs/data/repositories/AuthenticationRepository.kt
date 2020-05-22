package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.User
import com.chentir.favqs.data.entities.UserIdentifier
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.data.entities.UserSessionEntity
import com.chentir.favqs.data.services.CreateSessionService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler
import java.lang.Exception

class AuthenticationRepository(
    private val createSessionService: CreateSessionService,
    private val userSessionDao: UserSessionDao
) {
    suspend fun authenticate(username: String, password: String): Resource<UserSessionEntity> {
        return try {
            val userIdentifier = UserIdentifier(User(username, password))
            val userSessionEntity = createSessionService.session(userIdentifier)
            userSessionDao.insert(userSessionEntity)
            ResponseHandler.handleSuccess(userSessionEntity)
        } catch (e: Exception) {
            ResponseHandler.handleException(e)
        }
    }
}