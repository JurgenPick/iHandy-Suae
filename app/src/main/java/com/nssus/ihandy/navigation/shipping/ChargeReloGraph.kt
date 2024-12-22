package com.nssus.ihandy.navigation.shipping

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.shipping.chargerelo.ChargeReloRoute
import com.nssus.ihandy.ui.shipping.chargerelo.viewmodel.ChargeReloViewModel

sealed class ChargeReloScreen(val route: String) {
    object MainFillChargeReloScreen : ChargeReloScreen("main_fill_charge_relo_screen")
}

fun NavGraphBuilder.chargeReloGraph(
    navController: NavController,
    chargeReloVm: ChargeReloViewModel
) {
    navigation(
        startDestination = ChargeReloScreen.MainFillChargeReloScreen.route,
        route = GraphConstant.CHARGE_RELO
    ) {
        composable(ChargeReloScreen.MainFillChargeReloScreen.route) {
            ChargeReloRoute(navController, chargeReloVm)
        }
    }
}