package com.chentir.favqs.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chentir.favqs.databinding.ActivityLoginBinding
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // TODO: refactor
    binding = ActivityLoginBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.actionLogin.setOnClickListener {
      Timber.d("Login with ${binding.inputUsername} and ${binding.inputPassword}")
    }
  }
}

