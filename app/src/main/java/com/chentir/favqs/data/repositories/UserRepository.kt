package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.UserEntity
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.data.services.GetUserService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler
import java.lang.Exception

class UserRepository(
  private val getUserService: GetUserService,
  private val userSessionDao: UserSessionDao
) {
  suspend fun getUser(): Resource<UserEntity> {
    val userSessionEntity = userSessionDao.getLastUserSession()
    return try {
      val userEntity = getUserService.getUser(
                "application/json",
                "Token token=3076049eda9ba452981badda30fe2d47",
                userSessionEntity.userToken
      )
      ResponseHandler.handleSuccess(userEntity)
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }
}