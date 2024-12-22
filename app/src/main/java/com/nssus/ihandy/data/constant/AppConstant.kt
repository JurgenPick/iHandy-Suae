package com.nssus.ihandy.data.constant

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nssus.ihandy.model.home.DisplayHomeModel

object AppConstant {

    var NETWORK_IP: String? = null
//    var USER_ROLE = ""
    var USER_ROLE by mutableStateOf("")
    var USERNAME = ""
    var APP_TOKEN = ""
    var REFRESH_TOKEN = "" //Added the refresh token
    var MAIN_MENU by mutableStateOf(DisplayHomeModel())


}