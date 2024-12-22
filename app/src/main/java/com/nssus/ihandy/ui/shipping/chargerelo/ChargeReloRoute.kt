package com.nssus.ihandy.ui.shipping.chargerelo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.common.yardentry.YardEntryNavigateType
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.shipping.chargerelo.ChargeReloScreen
import com.nssus.ihandy.ui.shipping.chargerelo.viewmodel.ChargeReloViewModel

@Composable
fun ChargeReloRoute(
    navController: NavController,
    chargeReloVm: ChargeReloViewModel
) {
    val uiChargeReloSt by chargeReloVm.chargeReloUISt
    when {
        uiChargeReloSt.isLoading -> CustomLoading()
        uiChargeReloSt.isSuccess -> {
            when (uiChargeReloSt.navigateType) {
                ChargeReloNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    chargeReloVm.initNavigateData()
                }
                else -> Unit
            }
        }
        uiChargeReloSt.isError -> {

        }
    }
    ChargeReloScreen(
        uiChargeReloSt = uiChargeReloSt,
        onAction = chargeReloVm::action
    )
}