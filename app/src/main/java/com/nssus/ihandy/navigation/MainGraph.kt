package com.nssus.ihandy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nssus.ihandy.navigation.common.invTakingGraph
import com.nssus.ihandy.navigation.common.relabelGraph
import com.nssus.ihandy.navigation.common.shipByProGraph
import com.nssus.ihandy.navigation.common.validateGraph
import com.nssus.ihandy.navigation.common.yardEntryGraph
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.navigation.production.cvloadingGraph
import com.nssus.ihandy.navigation.production.deliveryCheckGraph
import com.nssus.ihandy.navigation.production.crfhReceiveGraph
import com.nssus.ihandy.navigation.production.rclTollingGraph
import com.nssus.ihandy.navigation.shipping.chargeReloGraph
import com.nssus.ihandy.navigation.shipping.pcShippigGraph
import com.nssus.ihandy.ui.home.viewmodel.HomeViewModel
import com.nssus.ihandy.ui.login.viewmodel.LoginViewModel
import com.nssus.ihandy.ui.common.inventorytaking.viewmodel.InvTakingViewModel
import com.nssus.ihandy.ui.common.validate.viewmodel.ValidateViewModel
import com.nssus.ihandy.ui.common.yardentry.viewmodel.YardEntryViewModel
import com.nssus.ihandy.ui.common.relabel.viewmodel.ReLabelViewModel
import com.nssus.ihandy.ui.common.shipbypro.viewmodel.ShipByProViewModel
import com.nssus.ihandy.ui.production.crfhrec.viewmodel.CRFHReceiveViewModel
import com.nssus.ihandy.ui.production.cvloading.viewmodel.CVLoadingViewModel
import com.nssus.ihandy.ui.production.delcheck.viewmodel.DeliveryCheckViewModel
import com.nssus.ihandy.ui.production.rcltolling.viewmodel.RCLTollingViewModel
import com.nssus.ihandy.ui.shipping.chargerelo.viewmodel.ChargeReloViewModel
import com.nssus.ihandy.ui.shipping.pcshipping.viewmodel.PcShippingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainGraph(
    navController: NavController
) {
    val homeVm = koinViewModel<HomeViewModel>()
    val yardEntryVm = koinViewModel<YardEntryViewModel>()
    val invTakingVm = koinViewModel<InvTakingViewModel>()
    val loginVm = koinViewModel<LoginViewModel>()
    val validateVm = koinViewModel<ValidateViewModel>()
    val relabelVm = koinViewModel<ReLabelViewModel>()
    val shipByProVM = koinViewModel<ShipByProViewModel>()
    //Production
    val cvLoadingVm = koinViewModel<CVLoadingViewModel>()
    val deliveryCheckVm = koinViewModel<DeliveryCheckViewModel>()
    val crfhReceiveVm = koinViewModel<CRFHReceiveViewModel>()
    val rclTollingVm = koinViewModel<RCLTollingViewModel>()
    //Shipping
    val chargeReloVm = koinViewModel<ChargeReloViewModel>()
    val pcShippingVM = koinViewModel<PcShippingViewModel>()


    NavHost(
        navController = navController as NavHostController,
        route = GraphConstant.MAIN,
        startDestination = GraphConstant.HOME
    ) {
        homeGraph(navController, homeVm)
        //Common
        yardEntryGraph(navController, yardEntryVm)
        invTakingGraph(navController, invTakingVm)
        authGraph(navController, loginVm)
        validateGraph(navController, validateVm)
        relabelGraph(navController, relabelVm)
        shipByProGraph(navController, shipByProVM)
        //Production
        cvloadingGraph(navController, cvLoadingVm)
        deliveryCheckGraph(navController, deliveryCheckVm)
        crfhReceiveGraph(navController, crfhReceiveVm)
        rclTollingGraph(navController, rclTollingVm)
        //Shipping
        chargeReloGraph(navController, chargeReloVm)
        pcShippigGraph(navController, pcShippingVM)
    }
}