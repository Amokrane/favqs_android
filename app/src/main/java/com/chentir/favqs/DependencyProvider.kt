package com.chentir.favqs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {
  private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://favqs.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }
}