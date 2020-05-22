package com.chentir.favqs.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.chentir.favqs.DependencyProvider
import com.chentir.favqs.R
import com.chentir.favqs.data.local.UserSessionEntity
import com.chentir.favqs.ui.viewmodels.SplashViewModel
import com.chentir.favqs.ui.viewmodels.factories.SplashViewModelFactory

class SplashActivity : AppCompatActivity() {
  private lateinit var viewModel: SplashViewModel
  private lateinit var viewModelFactory: SplashViewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    val userSessionDao = DependencyProvider.provideUserSessionDao(applicationContext)
    viewModelFactory =
      SplashViewModelFactory(
          userSessionDao
      )
    viewModel = SplashViewModel(userSessionDao)
    val liveData = viewModel.checkUserSession()
    liveData.observe(this, Observer<UserSessionEntity?> { t ->
      var intent: Intent?
      intent = if (t == null) {
        Intent(this, LoginActivity::class.java)
      } else {
        Intent(this, QuotesActivity::class.java)
      }
      startActivity(intent)
    })
  }
}