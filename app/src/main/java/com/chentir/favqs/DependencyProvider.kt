package com.chentir.favqs

import android.content.Context
import androidx.room.Room
import com.chentir.favqs.data.local.FavqsDatabase
import com.chentir.favqs.data.local.UserSessionDao
import com.chentir.favqs.data.local.UserSessionEntity
import com.chentir.favqs.data.repositories.AuthenticationRepository
import com.chentir.favqs.data.services.CreateSessionService
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {
  fun provideAuthenticationRepository(): AuthenticationRepository {
    return AuthenticationRepository(provideCreateSessionService())
  }

  fun provideUserSessionDao(applicationContext: Context): UserSessionDao {
    return provideDb(applicationContext).userSessionDao()
  }

  private fun provideCreateSessionService(): CreateSessionService {
    val retrofit = provideRetrofit()
    return retrofit.create(CreateSessionService::class.java)
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

