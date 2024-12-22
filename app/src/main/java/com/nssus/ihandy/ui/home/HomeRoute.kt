package com.nssus.ihandy.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.nssus.ihandy.model.home.HomeNavigateType
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.home.viewmodel.HomeViewModel

@Composable
fun HomeRoute(
    navController: NavController,
    homeVm: HomeViewModel
) {
    val uiHomeSt by homeVm.homeUISt

//    LaunchedEffect(Unit) {
//        homeVm.getMenuData()
//    }

    when {
        uiHomeSt.isLoading -> CustomLoading()
        uiHomeSt.isSuccess -> {
            when (uiHomeSt.navigateType) {
                HomeNavigateType.GO_TO_SELECTED_MENU -> {
                    navController.navigate(uiHomeSt.selectedRoute) {
                        launchSingleTop = true
                    }
                    homeVm.initNavigateData()
                }
                else -> Unit
            }
        }
        uiHomeSt.isError -> {

        }
    }

    HomeScreen(
//        menuData = uiHomeSt.menuData,
        onMenuItemClick = homeVm::selectMenu
    )
}