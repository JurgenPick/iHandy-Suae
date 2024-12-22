package com.nssus.ihandy.navigation.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.common.shipbypro.ShipByProRoute
import com.nssus.ihandy.ui.common.shipbypro.coillist.ShipByProCoilListRoute
import com.nssus.ihandy.ui.common.shipbypro.viewmodel.ShipByProViewModel


sealed class ShipByProScreen(val route: String){
    object MainFillShipByProScreen :ShipByProScreen("main_fill_ship_by_pro_screen")
    object CoilListShipByProScreen :ShipByProScreen("coil_list_ship_by_pro_screen")
}

fun NavGraphBuilder.shipByProGraph(
    navController: NavController ,
    shipByProVM: ShipByProViewModel
){
    navigation(
        startDestination = ShipByProScreen.MainFillShipByProScreen.route,
        route = GraphConstant.SHIP_BY_PRO
    ){
        composable(ShipByProScreen.MainFillShipByProScreen.route){
            ShipByProRoute(navController, shipByProVM)
        }
        composable(ShipByProScreen.CoilListShipByProScreen.route){
            ShipByProCoilListRoute(navController, shipByProVM)
        }
    }
}