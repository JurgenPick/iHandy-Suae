package com.nssus.ihandy.navigation.production

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.production.cvloading.CVLoadingRoute
import com.nssus.ihandy.ui.production.cvloading.viewmodel.CVLoadingViewModel

sealed class CVLoadingScreen(val route: String) {
    object MainFillCVLoadingScreen : CVLoadingScreen("main_fill_cv_loading_screen")
}

fun NavGraphBuilder.cvloadingGraph(
    navController: NavController,
    cvLoadingVm: CVLoadingViewModel
) {
    navigation(
        startDestination = CVLoadingScreen.MainFillCVLoadingScreen.route,
        route = GraphConstant.CV_LOADING
    ) {
        composable(CVLoadingScreen.MainFillCVLoadingScreen.route) {
            CVLoadingRoute(navController, cvLoadingVm)
        }
    }
}