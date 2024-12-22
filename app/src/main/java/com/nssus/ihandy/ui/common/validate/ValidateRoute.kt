package com.nssus.ihandy.ui.common.validate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.validate.ValidateNavigateType
import com.nssus.ihandy.model.common.validate.ValidateUIStateModel
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.common.validate.viewmodel.ValidateViewModel

@Composable
fun ValidateRoute(
    navController: NavController,
    validateVm: ValidateViewModel
) {
    val uiValidateSt by validateVm.validateUISt

    when {
        uiValidateSt.isLoading -> CustomLoading()
        uiValidateSt.isSuccess -> {
            when (uiValidateSt.navigateType) {
                ValidateNavigateType.GO_BACK -> {
                    navController.popBackStack()
                    validateVm.initNavigateData()
                }

                else -> Unit
            }
        }

        uiValidateSt.isError -> {

        }
    }

    ValidateScreen(
        uiValidateSt = uiValidateSt, //
        onAction = validateVm::action
    )
}