package com.nssus.ihandy.ui.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.constant.AppConstant.USER_ROLE
import com.nssus.ihandy.data.constant.HomeMenuConstant.ADMIN_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.HOT_COIL_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.PACKING_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.PRODUCTION_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.SHIPPING_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.TEST_LAB_OP_MENU
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_ADMIN
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_HOT_COIL
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_PACKING
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_PRODUCTION
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_SHIPPING
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_TEST_LAB
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.home.HomeErrorModel
import com.nssus.ihandy.model.home.HomeErrorType
import com.nssus.ihandy.model.home.HomeMenuItem
import com.nssus.ihandy.model.home.HomeNavigateType
import com.nssus.ihandy.model.home.HomeUIStateModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(

) : ViewModel() {
    private val _homeUISt = mutableStateOf(HomeUIStateModel())
    val homeUISt: State<HomeUIStateModel> = _homeUISt

//    fun getMenuData() {
//        viewModelScope.launch { delay(1000L) }
//        _homeUISt.value = HomeUIStateModel( // init state model
//            menuData = when(USER_ROLE){
//                USER_ROLE_ADMIN -> ADMIN_OP_MENU
//                USER_ROLE_PACKING -> PACKING_OP_MENU
//                USER_ROLE_SHIPPING -> SHIPPING_OP_MENU
//                USER_ROLE_HOT_COIL -> HOT_COIL_OP_MENU
//                USER_ROLE_TEST_LAB -> TEST_LAB_OP_MENU
//                else ->{
//                    if (USER_ROLE.contains(USER_ROLE_PRODUCTION)) PRODUCTION_OP_MENU
//                    else DisplayHomeModel()
//                }
//            }
//        )
//    }

    fun selectMenu(selectedMenu: HomeMenuItem) {
        _homeUISt.value = onHomeUIStateSuccess(
            navigateType = HomeNavigateType.GO_TO_SELECTED_MENU
        ).copy(
            selectedRoute = selectedMenu.route
        )
    }

    fun initNavigateData() {
        _homeUISt.value = onHomeUIStateSuccess()
    }

    private fun onHomeUIStateSuccess(
        navigateType: HomeNavigateType = HomeNavigateType.NONE,
        successMsg: String? = null
    ): HomeUIStateModel = _homeUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onHomeUIStateError(
        homeErrorType: HomeErrorType = HomeErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): HomeUIStateModel = _homeUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = HomeNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = HomeErrorModel(
            homeErrorType = homeErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onHomeUIStateLoading(isLoading: Boolean = true) {
        _homeUISt.value = _homeUISt.value.copy(isLoading = isLoading)
    }
}