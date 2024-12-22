package com.nssus.ihandy.ui.common.relabel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.common.relabel.ReLabelNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.common.relabel.viewmodel.ReLabelViewModel

@Composable
fun ReLabelRoute(
    navController: NavController,
    relabelVm: ReLabelViewModel
) {
    val uiReLabelSt by relabelVm.relabelUISt

LaunchedEffect(Unit) {
    relabelVm.initData()
}

    when {
        uiReLabelSt.isLoading -> CustomLoading()
        uiReLabelSt.isSuccess -> {
            when (uiReLabelSt.navigateType) {
                ReLabelNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    relabelVm.initNavigateData()
                }
                else -> Unit
            }
        }
        uiReLabelSt.isError -> {

        }
    }

    ReLabelScreen(
        dataLs = uiReLabelSt.dataLs,
        uiReLabelSt = uiReLabelSt,
        onAction = relabelVm::action
    )
}