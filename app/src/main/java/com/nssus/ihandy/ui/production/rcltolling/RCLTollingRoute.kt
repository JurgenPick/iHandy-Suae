package com.nssus.ihandy.ui.production.rcltolling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.production.rcltolling.RCLTollingAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingDialogAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingNavigateType
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CompleteDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.production.rcltolling.viewmodel.RCLTollingViewModel
@Composable
fun RCLTollingRoute(
    navController: NavController,
    rclTollingVM: RCLTollingViewModel
) {
    val uiRCLTollingSt by rclTollingVM.rclTollingUISt
    // Initialize data when the route is loaded
    LaunchedEffect(Unit) {
        rclTollingVM.initData()
    }
    // Handle various UI states and navigation events
    when {
        uiRCLTollingSt.isLoading -> CustomLoading()
        uiRCLTollingSt.isError -> {
            BaseMsgWithButtonDialog(
                message = uiRCLTollingSt.message.orEmpty(),
                icon = R.drawable.ic_dialog_red_cross,
                onLeftButtonClick = { rclTollingVM.action(RCLTollingAction.InitNavigateData) }
            )
        }
        uiRCLTollingSt.isSuccess -> {
            when (uiRCLTollingSt.navigateType) {
                RCLTollingNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    rclTollingVM.action(RCLTollingAction.InitNavigateData)
                }
                RCLTollingNavigateType.DISPLAY_DIALOG_SUCCESS -> {
                    CompleteDialog(
                        message = uiRCLTollingSt.message.orEmpty(),
                        onDialogButtonClick = { rclTollingVM.action(RCLTollingAction.InitNavigateData) }
                    )
                }
                else -> {}
            }
        }
    }
    // Render the RCL Tolling screen
    RCLTollingScreen(
        uiRCLTollingSt = uiRCLTollingSt,
        onAction = rclTollingVM::action,
        onDialogAction = rclTollingVM::actionFormDialog
    )
}