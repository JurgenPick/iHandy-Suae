package com.nssus.ihandy.navigation.production

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.production.delcheck.DeliveryCheckRoute
import com.nssus.ihandy.ui.production.delcheck.viewmodel.DeliveryCheckViewModel
// Define sealed class for Delivery Check screens
sealed class DeliveryCheckScreen(val route: String) {
    object MainDeliveryCheckScreen : DeliveryCheckScreen("main_delivery_check_screen")
}
// Define navigation graph for Delivery Check
fun NavGraphBuilder.deliveryCheckGraph(
    navController: NavController,
    deliveryCheckVm: DeliveryCheckViewModel
) {
    navigation(
        startDestination = DeliveryCheckScreen.MainDeliveryCheckScreen.route,
        route = GraphConstant.DEL_CHECK
    ) {
        composable(DeliveryCheckScreen.MainDeliveryCheckScreen.route) {
            DeliveryCheckRoute(navController, deliveryCheckVm)
        }
    }
}