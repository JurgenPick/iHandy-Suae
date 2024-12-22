package com.nssus.ihandy.ui.common.shipbypro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProDialogAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProNavigateType
import com.nssus.ihandy.navigation.common.ShipByProScreen
import com.nssus.ihandy.ui.basecomposable.BaseMsgWithButtonDialog
import com.nssus.ihandy.ui.basecomposable.CompleteDialog
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.common.shipbypro.viewmodel.ShipByProViewModel
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.WarningRed

@Composable
fun ShipByProRoute(
    navController: NavController ,
    shipByProVM: ShipByProViewModel
){
    val uiShipByProSt by shipByProVM.shipByProUISt

    LaunchedEffect(Unit) {
        shipByProVM.initData()
    }

    when {
        uiShipByProSt.isLoading ->{println("Route none")
            CustomLoading() }
        uiShipByProSt.isSuccess -> {
            println("Route Success")
            when (uiShipByProSt.navigateType) {
                ShipByProNavigateType.GO_BACK-> {
                    navController.popBackStack()
                    shipByProVM.action(ShipByProAction.InitNavigateData)
                }
                ShipByProNavigateType.DISPLAY_DIALOG_SEND_MQ -> {
                    WarningDialog(
                        message = uiShipByProSt.message ?: " ",
                        onLeftDialogButtonClick = { shipByProVM.actionFormDialog(ShipByProDialogAction.ClickConfirmSendMQ) },
                        onRightDialogButtonClick = { shipByProVM.action(ShipByProAction.InitNavigateData) }
                        )
                }
                ShipByProNavigateType.DISPLAY_DIALOG_SUCCESS -> {
                    CompleteDialog(
                        message = uiShipByProSt.message ?: "",
                        onDialogButtonClick = {shipByProVM.actionFormDialog(ShipByProDialogAction.ClickContinueDialogButton)}
                    )
                }
                ShipByProNavigateType.DISPLAY_DIALOG_WARNING -> {
                    WarningDialog(
                        message = uiShipByProSt.message ?: "",
                        onLeftDialogButtonClick = { shipByProVM.actionFormDialog(ShipByProDialogAction.ClickContinueDialogButton) },
                        onRightDialogButtonClick = { shipByProVM.action(ShipByProAction.InitNavigateData) }
                        )
                }
                ShipByProNavigateType.GO_TO_COIL_DETAIL_LS -> {
                    navController.navigate(ShipByProScreen.CoilListShipByProScreen.route)
                    shipByProVM.action(ShipByProAction.InitNavigateData)
                }
                else -> {}
            }
        }
        uiShipByProSt.isError -> {
            BaseMsgWithButtonDialog(
                message = (if (uiShipByProSt.errorBody?.errorMsg.isNullOrEmpty()) stringResource(id = uiShipByProSt.errorBody?.shipByProErrorType?.errorMsgId ?: R.string.dash_string)
                else uiShipByProSt.errorBody?.errorMsg) ?: "-" ,
                icon = R.drawable.ic_dialog_red_cross,
                messageColor = Black50,
                leftButtonTextColor = Color.White,
                leftButtonColor = WarningRed,
                leftButtonBorderColor = WarningRed,
                onLeftButtonClick = { shipByProVM.actionFormDialog(ShipByProDialogAction.ClickLeftShipByProDialogButton) })
        }
    }
    ShipByProScreen(
        uiShipByProSt = uiShipByProSt,
        onAction = shipByProVM::action,
        onDialogAction = shipByProVM::actionFormDialog
    )
}