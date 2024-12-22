package com.nssus.ihandy.ui.common.inventorytaking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.common.inventorytaking.InvTakingNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.common.inventorytaking.viewmodel.InvTakingViewModel

@Composable
fun InvTakingRoute(
    navController: NavController,
    invTakingVm: InvTakingViewModel
) {
    val uiInvTakingSt by invTakingVm.invTakingUISt
    when {
        uiInvTakingSt.isLoading -> CustomLoading()
        uiInvTakingSt.isSuccess -> {
            when (uiInvTakingSt.navigateType) {
                InvTakingNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    invTakingVm.initNavigateData()
                }
                else -> Unit
            }
        }
        uiInvTakingSt.isError -> {

        }
    }

    InvTakingScreen(
        uiInvTakingSt = uiInvTakingSt,
        onAction = invTakingVm::action
    )
}