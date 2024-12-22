package com.nssus.ihandy.navigation.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.common.validate.ValidateRoute
import com.nssus.ihandy.ui.common.validate.viewmodel.ValidateViewModel

sealed class ValidateScreen(val route: String) {
    object MainFillValidateScreen : ValidateScreen("main_fill_validate_screen")
}

fun NavGraphBuilder.validateGraph(
    navController: NavController,
    validateVm: ValidateViewModel
) {
    navigation(
        startDestination = ValidateScreen.MainFillValidateScreen.route,
        route = GraphConstant.VALIDATE
    ) {
        composable(ValidateScreen.MainFillValidateScreen.route) {
            ValidateRoute(navController, validateVm)
        }
    }
}