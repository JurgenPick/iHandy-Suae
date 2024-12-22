package com.nssus.ihandy.data.network.exception

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Connection"
}