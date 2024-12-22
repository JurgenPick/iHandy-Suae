package com.nssus.ihandy.navigation.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.common.inventorytaking.InvTakingRoute
import com.nssus.ihandy.ui.common.inventorytaking.viewmodel.InvTakingViewModel

sealed class InvTakingScreen(val route: String) {
    object MainFillInvTakingScreen : InvTakingScreen("main_fill_inv_taking_screen")
}

fun NavGraphBuilder.invTakingGraph(
    navController: NavController,
    invTakingVm: InvTakingViewModel
) {
    navigation(
        startDestination = InvTakingScreen.MainFillInvTakingScreen.route,
        route = GraphConstant.INVENTORY_TAKING
    ) {
        composable(InvTakingScreen.MainFillInvTakingScreen.route) {
            InvTakingRoute(navController, invTakingVm)
        }
    }
}