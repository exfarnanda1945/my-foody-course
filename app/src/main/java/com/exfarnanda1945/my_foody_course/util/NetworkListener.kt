package com.exfarnanda1945.my_foody_course.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener : ConnectivityManager.NetworkCallback() {
    private val isNetworkIsAvailable = MutableStateFlow<Boolean>(false)

    fun checkNetworkCapabilities(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        if (capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
            isConnected = true
        }

        isNetworkIsAvailable.value = isConnected
        return isNetworkIsAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkIsAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkIsAvailable.value = false
    }
}