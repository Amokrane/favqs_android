package com.chentir.favqs.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.chentir.favqs.DependencyProvider
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.databinding.ActivityLoginBinding
import com.chentir.favqs.ui.viewmodels.LoginViewModel
import com.chentir.favqs.ui.viewmodels.factories.LoginViewModelFactory
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  private lateinit var viewModel: LoginViewModel
  private lateinit var viewModelFactory: LoginViewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    val authenticationRepository =
      DependencyProvider.provideAuthenticationRepository(applicationContext)
    viewModelFactory = LoginViewModelFactory(authenticationRepository)
    viewModel = viewModelFactory.create(LoginViewModel::class.java)

    binding.actionLogin.setOnClickListener {
      hideKeyboard()
      val liveData = viewModel.createSession(
          binding.inputUsername.text.toString(),
          binding.inputPassword.text.toString()
      )

      liveData.observe(this, Observer {
        when (it) {
          is Lce.Error -> {
            Timber.d("Login error ${it.message}")
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
                .show()
          }
          is Lce.Success -> {
            Timber.d("Login success ${it.data}")
            val intent = Intent(this, ProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
          }
        }
      })
    }
  }

  // FIXME
  private fun hideKeyboard() {
    var view = this.currentFocus
    if (view == null) {
      view = View(this)
    }
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
  }
}