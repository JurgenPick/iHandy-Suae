package com.nssus.ihandy.navigation.shipping

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.shipping.pcshipping.PcShippingRoute
import com.nssus.ihandy.ui.shipping.pcshipping.viewmodel.PcShippingViewModel

sealed class PcShippingScreen(val route : String){
    object MainFillPcShippingScreen : PcShippingScreen("main_fill_pc_shipping_screen")
}

fun NavGraphBuilder.pcShippigGraph(
    navController: NavController,
    pcShippingVM: PcShippingViewModel
){
    navigation(
        startDestination = PcShippingScreen.MainFillPcShippingScreen.route,
        route = GraphConstant.PC_SHIPPING
    ){
        composable(PcShippingScreen.MainFillPcShippingScreen.route){
            PcShippingRoute(navController, pcShippingVM)
        }
    }
}