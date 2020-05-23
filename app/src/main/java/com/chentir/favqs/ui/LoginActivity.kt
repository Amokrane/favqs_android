package com.chentir.favqs.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.chentir.favqs.DependencyProvider
import com.chentir.favqs.R
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.databinding.ActivityLoginBinding
import com.chentir.favqs.ui.viewmodels.LoginViewModel
import com.chentir.favqs.ui.viewmodels.factories.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  private lateinit var viewModel: LoginViewModel
  private lateinit var viewModelFactory: LoginViewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    val authenticationRepository =
      DependencyProvider.provideAuthenticationRepository(applicationContext)
    viewModelFactory = LoginViewModelFactory(authenticationRepository)
    viewModel = viewModelFactory.create(LoginViewModel::class.java)

    binding.actionLogin.setOnClickListener {
      val liveData = viewModel.createSession(
          binding.inputUsername.text.toString(),
          binding.inputPassword.text.toString()
      )

      liveData.observe(this, Observer {
        when (it) {
          is Lce.Error -> {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
                .show()
          }
          is Lce.Success -> {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
          }
        }
      })
    }
  }

  override fun startActivity(intent: Intent?) {
    super.startActivity(intent)
    overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out)
  }
}