package com.chentir.favqs

import com.chentir.favqs.data.repositories.AuthenticationRepository
import com.chentir.favqs.data.services.CreateSessionService
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {
  fun provideAuthenticationRepository(): AuthenticationRepository {
    return AuthenticationRepository(provideCreateSessionService())
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
}