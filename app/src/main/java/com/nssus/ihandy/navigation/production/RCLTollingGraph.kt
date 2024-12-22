package com.nssus.ihandy.navigation.production

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.production.rcltolling.RCLTollingRoute
import com.nssus.ihandy.ui.production.rcltolling.viewmodel.RCLTollingViewModel
// Define sealed class for RCL Tolling screens
sealed class RCLTollingScreen(val route: String) {
    object MainRCLTollingScreen : RCLTollingScreen("main_rcl_tolling_screen")
}
// Define navigation graph for RCL Tolling
fun NavGraphBuilder.rclTollingGraph(
    navController: NavController,
    rclTollingVm: RCLTollingViewModel
) {
    navigation(
        startDestination = RCLTollingScreen.MainRCLTollingScreen.route,
        route = GraphConstant.RCL_TOLLING
    ) {
        composable(RCLTollingScreen.MainRCLTollingScreen.route) {
            RCLTollingRoute(navController, rclTollingVm)
        }
    }
}