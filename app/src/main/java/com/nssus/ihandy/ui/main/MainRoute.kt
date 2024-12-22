package com.nssus.ihandy.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.nssus.ihandy.data.extension.restartIHandyApp
import com.nssus.ihandy.model.main.MainNavigateType
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.main.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoute(
    navController: NavController
) {
    val context = LocalContext.current

    val mainVm = koinViewModel<MainViewModel>()

    val uiMainSt by mainVm.mainUISt

    LaunchedEffect(
        Unit
    ) {mainVm.getUserRole() }

    when {
        uiMainSt.isLoading -> CustomLoading()
        uiMainSt.isSuccess -> {
            when (uiMainSt.navigateType) {

                MainNavigateType.GO_TO_RESTART_APP -> {
                    context.restartIHandyApp()
                    mainVm.initNavigateData()
                }
                else -> Unit
            }
        }
        uiMainSt.isError -> {
            //To-do

        }
    }

    MainScreen(
        onLogoutIconClick = mainVm::logout
    )
}