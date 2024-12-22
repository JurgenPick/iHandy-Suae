package com.nssus.ihandy.ui.production.delcheck

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckDialogAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CompleteDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.production.delcheck.viewmodel.DeliveryCheckViewModel
@Composable
fun DeliveryCheckRoute(
    navController: NavController,
    deliveryCheckVM: DeliveryCheckViewModel
) {
    val uiDeliveryCheckSt by deliveryCheckVM.deliveryCheckUISt
    // Initialize data when the route is loaded
    LaunchedEffect(Unit) {
        deliveryCheckVM.initData()
    }
    // Handle various UI states and navigation events
    when {
        uiDeliveryCheckSt.isLoading -> CustomLoading()
        uiDeliveryCheckSt.isError -> {
            BaseMsgWithButtonDialog(
                message = uiDeliveryCheckSt.message.orEmpty(),
                icon = R.drawable.ic_dialog_red_cross,
                onLeftButtonClick = { deliveryCheckVM.action(DeliveryCheckAction.InitNavigateData) }
            )
        }
        uiDeliveryCheckSt.isSuccess -> {
            when (uiDeliveryCheckSt.navigateType) {
                DeliveryCheckNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    deliveryCheckVM.action(DeliveryCheckAction.InitNavigateData)
                }
                DeliveryCheckNavigateType.DISPLAY_DIALOG_SUCCESS -> {
                    CompleteDialog(
                        message = uiDeliveryCheckSt.message.orEmpty(),
                        onDialogButtonClick = { deliveryCheckVM.action(DeliveryCheckAction.InitNavigateData) }
                    )
                }
                else -> {}
            }
        }
    }
    // Render the Delivery Check screen
    DeliveryCheckScreen(
        uiDeliveryCheckSt = uiDeliveryCheckSt,
        onAction = deliveryCheckVM::action,
        onDialogAction = deliveryCheckVM::actionFormDialog
    )
}