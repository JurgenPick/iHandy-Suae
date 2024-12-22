package com.nssus.ihandy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nssus.ihandy.data.util.NetworkUtil.getLocalIpAddress
import com.nssus.ihandy.ui.main.IHandyApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        getLocalIpAddress()

        setContent {
            IHandyApp()
        }
    }

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(LocaleUtils.setLocale(base, null))
//    }

}