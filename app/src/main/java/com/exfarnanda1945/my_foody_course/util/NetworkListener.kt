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

        val network= connectivityManager.activeNetwork
        if(network ==null){
            isNetworkIsAvailable.value = false
            return isNetworkIsAvailable
        }

        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if(networkCapabilities == null){
            isNetworkIsAvailable.value = false
            return isNetworkIsAvailable
        }



        return when{
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                isNetworkIsAvailable.value = true
                isNetworkIsAvailable
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                isNetworkIsAvailable.value = true
                isNetworkIsAvailable
            }
            else -> {
                isNetworkIsAvailable.value = false
                isNetworkIsAvailable
            }
        }
    }

    override fun onAvailable(network: Network) {
        isNetworkIsAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkIsAvailable.value = false
    }
}