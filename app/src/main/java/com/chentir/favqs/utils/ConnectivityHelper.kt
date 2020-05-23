package com.chentir.favqs.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityHelper(private val appContext: Context) {
  fun isConnected(): Boolean {
    val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
  }
}