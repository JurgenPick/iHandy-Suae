package com.nssus.ihandy.navigation.production

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.production.crfhrec.CRFHReceiveRoute
import com.nssus.ihandy.ui.production.crfhrec.viewmodel.CRFHReceiveViewModel
// Define sealed class for CRFH Receive screens
sealed class CRFHReceiveScreen(val route: String) {
    object MainCRFHReceiveScreen : CRFHReceiveScreen("main_crfh_receive_screen")
}
// Define navigation graph for CRFH Receive
fun NavGraphBuilder.crfhReceiveGraph(
    navController: NavController,
    crfhReceiveVm: CRFHReceiveViewModel
) {
    navigation(
        startDestination = CRFHReceiveScreen.MainCRFHReceiveScreen.route,
        route = GraphConstant.CRFH_RECEIVE
    ) {
        composable(CRFHReceiveScreen.MainCRFHReceiveScreen.route) {
            CRFHReceiveRoute(navController, crfhReceiveVm)
        }
    }
}