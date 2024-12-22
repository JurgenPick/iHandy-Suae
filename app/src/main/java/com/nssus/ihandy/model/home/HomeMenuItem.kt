package com.nssus.ihandy.model.home

import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.navigation.constant.GraphConstant
import java.io.Serializable

sealed class HomeMenuItem(
    @StringRes val menuNameId: Int,
    val route: String
) : Serializable {
    //Common
    object YardEntryScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_yard_entry_title,
            route = GraphConstant.YARD_ENTRY
        )
    object InventoryTakingScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_inventory_taking_title,
            route = GraphConstant.INVENTORY_TAKING
        )
    object ValidateScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_validate_title,
            route = GraphConstant.VALIDATE
        )
    object ReLabelScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_relabel_title,
            route = GraphConstant.RELABEL
        )
    object ShipByProScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_ship_by_product_title,
            route = GraphConstant.SHIP_BY_PRO
        )

    //Production
    object CVLoadingScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_cv_loading_title,
            route = GraphConstant.CV_LOADING
        )

    object DelCheckScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_del_check_title,
            route = GraphConstant.DEL_CHECK
        )

    object CRFHReceiveScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_crfh_receive_title,
            route = GraphConstant.CRFH_RECEIVE
        )
    object RCLTollingScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_rcl_tolling_title,
            route = GraphConstant.RCL_TOLLING
        )


    //Shipping
    object ChargeReloScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_charge_relo_title,
            route = GraphConstant.CHARGE_RELO
        )
    object PcShippingScreen :
            HomeMenuItem(
                menuNameId = R.string.menu_pc_shipping_title,
                route = GraphConstant.PC_SHIPPING
            )
}