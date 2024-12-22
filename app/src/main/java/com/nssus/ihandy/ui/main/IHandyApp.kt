package com.nssus.ihandy.ui.main

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.navigation.authGraph
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.login.viewmodel.LoginViewModel
import com.nssus.ihandy.ui.permission.CheckAndRequestPermission
import com.nssus.ihandy.ui.theme.IHandyTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun IHandyApp(
//    iHandyVm: IHandyVm = koinViewModel(),
    loginVm: LoginViewModel = koinViewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        //do nothing
    }

    IHandyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            //Permission for Chucker on Android 13+
            if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                CheckAndRequestPermission(
                    permission = Manifest.permission.POST_NOTIFICATIONS,
                    launcher = launcher
                )
            }

            NavHost(
                navController = navController,
                startDestination = GraphConstant.AUTH,
                route = GraphConstant.ROOT
            ) {
                authGraph(navController, loginVm)

                composable(GraphConstant.MAIN) {
                    MainRoute(navController)
                }
            }
        }
    }
}