package com.nssus.ihandy.navigation.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.common.yardentry.YardEntryRoute
import com.nssus.ihandy.ui.common.yardentry.viewmodel.YardEntryViewModel

sealed class YardEntryScreen(val route: String) {
    object MainFillYardEntryScreen : YardEntryScreen("main_fill_yard_entry_screen")
}

fun NavGraphBuilder.yardEntryGraph(
    navController: NavController,
    yardEntryVm: YardEntryViewModel
) {
    navigation(
        startDestination = YardEntryScreen.MainFillYardEntryScreen.route,
        route = GraphConstant.YARD_ENTRY
    ) {
        composable(YardEntryScreen.MainFillYardEntryScreen.route) {
            YardEntryRoute(navController, yardEntryVm)
        }
    }
}