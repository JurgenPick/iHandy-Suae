package com.nssus.ihandy.ui.common.shipbypro.coillist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProDialogAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.WarningDialog
import com.nssus.ihandy.ui.common.shipbypro.viewmodel.ShipByProViewModel

@Composable
fun ShipByProCoilListRoute(
    navController: NavController,
    shipByProVM: ShipByProViewModel
){
    val uiShipByPrSt by shipByProVM.shipByProUISt

    when{
        uiShipByPrSt.isLoading ->{ CustomLoading() }
        uiShipByPrSt.isSuccess ->{
            when(uiShipByPrSt.navigateType){
                ShipByProNavigateType.BACK_TO_SHIP_BY_PRO_MAIN -> {
                    navController.popBackStack()
                    shipByProVM.action(ShipByProAction.InitNavigateData)
                }
                ShipByProNavigateType.DISPLAY_SHOW_REMOVE_COIL_DIALOG ->{
                    WarningDialog(
                        message = stringResource(id = R.string.ship_by_pro_delete_coil),
                        onLeftDialogButtonClick = {
                            shipByProVM.actionFormDialog(ShipByProDialogAction.ClickLeftDeleteCoilDialogButton)
                        },
                        onRightDialogButtonClick = {
                            shipByProVM.actionFormDialog(ShipByProDialogAction.ClickRightDeleteCoilDialogButton)
                        })
                }
                else -> {}
            }
        }
        uiShipByPrSt.isError -> {

        }
    }

    ShipByProCoilListScreen(
        uiShipByProSt = uiShipByPrSt,
        onAction = shipByProVM::action
    )
}