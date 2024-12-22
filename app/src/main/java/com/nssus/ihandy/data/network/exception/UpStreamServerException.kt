package com.nssus.ihandy.data.network.exception

import okio.IOException

class UpStreamServerException : IOException(){
    override val message: String
        get() = "UpStream Server"
}