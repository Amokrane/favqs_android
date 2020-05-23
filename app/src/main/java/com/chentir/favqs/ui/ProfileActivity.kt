package com.chentir.favqs.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.chentir.favqs.DependencyProvider
import com.chentir.favqs.R
import com.chentir.favqs.data.utils.Lce
import com.chentir.favqs.databinding.ActivityProfileBinding
import com.chentir.favqs.ui.viewmodels.ProfileViewModel
import com.chentir.favqs.ui.viewmodels.factories.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProfileBinding
  private lateinit var viewModel: ProfileViewModel
  private lateinit var viewModeFactory: ProfileViewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    binding = ActivityProfileBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    viewModeFactory =
      ProfileViewModelFactory(DependencyProvider.provideUserRepository(applicationContext))

    viewModel = viewModeFactory.create(ProfileViewModel::class.java)
    val liveData = viewModel.fetchUser()
    liveData.observe(this, Observer {
      when (it) {
        is Lce.Error -> {
          Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
              .show()
        }
        is Lce.Success -> {
          binding.labelUsername.text = it.data.login

          binding.actionViewQuotes.visibility = View.VISIBLE
          binding.actionViewQuotes.text =
            getString(R.string.action_view_quotes, it.data.quotesCount)

          binding.imgProfilePicture.load(it.data.picUrl)

          binding.actionViewQuotes.setOnClickListener {
            val intent = Intent(this, QuotesActivity::class.java)
            startActivity(intent)
          }
        }
      }
    })
  }

  override fun startActivity(intent: Intent?) {
    super.startActivity(intent)
    overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out)
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out)
  }
}