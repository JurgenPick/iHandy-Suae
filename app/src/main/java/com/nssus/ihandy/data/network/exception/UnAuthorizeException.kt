package com.nssus.ihandy.data.network.exception

import java.io.IOException

class UnAuthorizeException : IOException() {
    override val message: String
        get() = "Token Expired"
}