package com.chentir.favqs.data.repositories

import com.chentir.favqs.data.entities.UserEntity
import com.chentir.favqs.data.entities.UserSessionEntity
import com.chentir.favqs.data.local.UserDao
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.data.services.GetUserService
import com.chentir.favqs.data.utils.Resource
import com.chentir.favqs.data.utils.ResponseHandler
import com.chentir.favqs.utils.ConnectivityHelper
import java.lang.Exception

class UserRepository(
  private val getUserService: GetUserService,
  private val userSessionDao: UserSessionDao,
  private val userDao: UserDao,
  private val connectivityHelper: ConnectivityHelper
) {
  suspend fun getUser(): Resource<UserEntity> {
    val userSessionEntity = userSessionDao.getLastUserSession()
    var userEntity = UserEntity("", "", 0)
    return try {
      userEntity = if (connectivityHelper.isConnected()) {
        getUserFromRemote(userSessionEntity) ?: userEntity
      } else {
        userDao.get() ?: userEntity
      }

      ResponseHandler.handleSuccess(userEntity)
    } catch (e: Exception) {
      ResponseHandler.handleException(e)
    }
  }

  private suspend fun getUserFromRemote(userSessionEntity: UserSessionEntity): UserEntity? {
    val userEntity = getUserService.getUser(
        "application/json",
        "Token token=3076049eda9ba452981badda30fe2d47",
        userSessionEntity.userToken

    )
    userDao.insert(userEntity)
    return userEntity
  }
}