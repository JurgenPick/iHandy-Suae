package com.nssus.ihandy.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.nssus.ihandy.data.constant.AppConstant.NETWORK_IP
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections

object NetworkUtil {
    fun getLocalIpAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in Collections.list(interfaces)) {
                val addresses = networkInterface.inetAddresses
                for (inetAddress in Collections.list(addresses)) {
                    if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress) {
                        NETWORK_IP =  inetAddress.hostAddress
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    @SuppressLint("ObsoleteSdkInt")
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        val networks: Array<Network> = connectivityManager.allNetworks
        var isNetworkAvailable = false
        if (networks.isNotEmpty()) {
            for (network in networks) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                networkCapabilities?.let {
                    if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        isNetworkAvailable = true
                    }
                }
            }
        }
        return isNetworkAvailable
    }
}