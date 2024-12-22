package com.nssus.ihandy.data.constant

import com.nssus.ihandy.R
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.home.HomeMenuItem

object HomeMenuConstant {
    val PACKING_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_packing_title,
        menuLs = listOf(
            HomeMenuItem.InventoryTakingScreen,
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.ValidateScreen,
            HomeMenuItem.ReLabelScreen
        )
    )

    val SHIPPING_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_shipping_title,
        menuLs = listOf(
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.InventoryTakingScreen,
            HomeMenuItem.ValidateScreen,
            HomeMenuItem.ReLabelScreen,
            HomeMenuItem.ChargeReloScreen,
            HomeMenuItem.ShipByProScreen,
            HomeMenuItem.PcShippingScreen
        )
    )

    val PRODUCTION_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_production_title,
        menuLs = listOf(
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.CVLoadingScreen,
            HomeMenuItem.DelCheckScreen,
            HomeMenuItem.ReLabelScreen,
            HomeMenuItem.ShipByProScreen,
            HomeMenuItem.CRFHReceiveScreen,
            HomeMenuItem.RCLTollingScreen,
            HomeMenuItem.InventoryTakingScreen,
            HomeMenuItem.ValidateScreen
        )
    )

    val HOT_COIL_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_hot_coil_title,
        menuLs = listOf(
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.InventoryTakingScreen,

        )
    )

    val TEST_LAB_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_test_lab_title,
        menuLs = listOf(
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.InventoryTakingScreen
        )
    )

    val ADMIN_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_admin_title,
        menuLs = listOf(
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.InventoryTakingScreen,
            HomeMenuItem.CVLoadingScreen,
            HomeMenuItem.DelCheckScreen,
            HomeMenuItem.CRFHReceiveScreen,
            HomeMenuItem.RCLTollingScreen,
            HomeMenuItem.ValidateScreen,
            HomeMenuItem.ChargeReloScreen,
            HomeMenuItem.ReLabelScreen,
            HomeMenuItem.ShipByProScreen,
            HomeMenuItem.PcShippingScreen
        )
    )
}