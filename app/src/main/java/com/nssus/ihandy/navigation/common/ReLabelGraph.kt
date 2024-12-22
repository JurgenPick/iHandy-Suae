package com.nssus.ihandy.navigation.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.common.relabel.ReLabelRoute
import com.nssus.ihandy.ui.common.relabel.viewmodel.ReLabelViewModel

sealed class ReLabelScreen(val route: String) {
    object MainFillReLabelScreen : ReLabelScreen("main_fill_reLabel_screen")
}

fun NavGraphBuilder.relabelGraph(
    navController: NavController,
    relabelVm: ReLabelViewModel
) {
    navigation(
        startDestination = ReLabelScreen.MainFillReLabelScreen.route,
        route = GraphConstant.RELABEL
    ) {
        composable(ReLabelScreen.MainFillReLabelScreen.route) {
            ReLabelRoute(navController, relabelVm)
        }
    }
}