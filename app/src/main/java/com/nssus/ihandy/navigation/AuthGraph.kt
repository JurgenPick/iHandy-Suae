package com.nssus.ihandy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.login.LoginRoute
import com.nssus.ihandy.ui.login.viewmodel.LoginViewModel

sealed class AuthScreen(val route: String) {
    object LoginScreen : AuthScreen("login_screen")

}

fun NavGraphBuilder.authGraph(
    navController: NavController,
    loginVm: LoginViewModel
) {
    navigation(
        startDestination = AuthScreen.LoginScreen.route,
        route = GraphConstant.AUTH
    ) {
        composable(AuthScreen.LoginScreen.route) {
            LoginRoute(navController, loginVm)
        }
    }
}