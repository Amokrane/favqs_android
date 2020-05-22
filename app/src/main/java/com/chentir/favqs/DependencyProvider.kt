package com.chentir.favqs

import android.content.Context
import androidx.room.Room
import com.chentir.favqs.data.local.FavqsDatabase
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.data.repositories.AuthenticationRepository
import com.chentir.favqs.data.repositories.QuotesRepository
import com.chentir.favqs.data.repositories.UserRepository
import com.chentir.favqs.data.services.CreateSessionService
import com.chentir.favqs.data.services.GetQuotesService
import com.chentir.favqs.data.services.GetUserService
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {
  fun provideAuthenticationRepository(applicationContext: Context): AuthenticationRepository {
    return AuthenticationRepository(
        provideCreateSessionService(),
        provideUserSessionDao(applicationContext)
    )
  }

  fun provideUserRepository(applicationContext: Context): UserRepository {
    return UserRepository(
        provideGetUserService(),
        provideUserSessionDao(applicationContext)
    )
  }

  fun provideQuotesRepository(applicationContext: Context): QuotesRepository {
    return QuotesRepository(
        provideGetQuotesService()
    )
  }

  fun provideUserSessionDao(applicationContext: Context): UserSessionDao {
    return provideDb(applicationContext).userSessionDao()
  }

  private fun provideCreateSessionService(): CreateSessionService {
    val retrofit = provideRetrofit()
    return retrofit.create(CreateSessionService::class.java)
  }

  private fun provideGetUserService(): GetUserService {
    val retrofit = provideRetrofit()
    return retrofit.create(GetUserService::class.java)
  }

  private fun provideGetQuotesService(): GetQuotesService {
    val retrofit = provideRetrofit()
    return retrofit.create(GetQuotesService::class.java)
  }

  private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://favqs.com/")
        .client(provideOkHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }

  private fun provideOkHttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()
  }

  private fun provideDb(applicationContext: Context): FavqsDatabase {
    return Room.databaseBuilder(
        applicationContext,
        FavqsDatabase::class.java, "favqs_db"
    )
        .build()
  }

}

