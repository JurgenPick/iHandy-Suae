package com.nssus.ihandy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.home.HomeRoute
import com.nssus.ihandy.ui.home.viewmodel.HomeViewModel

sealed class HomeScreen(val route: String) {
    object MainHomeScreen : HomeScreen("main_home_screen")
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    homeVm: HomeViewModel
) {
    navigation(
        startDestination = HomeScreen.MainHomeScreen.route,
        route = GraphConstant.HOME
    ) {
        composable(HomeScreen.MainHomeScreen.route) {
            HomeRoute(navController, homeVm)
        }
    }
}