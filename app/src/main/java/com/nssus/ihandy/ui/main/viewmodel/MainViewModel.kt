package com.nssus.ihandy.ui.main.viewmodel

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.constant.AppConstant.APP_TOKEN
import com.nssus.ihandy.data.constant.AppConstant.MAIN_MENU
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
import com.nssus.ihandy.data.usecase.AuthenUseCase
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.main.MainErrorModel
import com.nssus.ihandy.model.main.MainErrorType
import com.nssus.ihandy.model.main.MainNavigateType
import com.nssus.ihandy.model.main.MainUIStateModel
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.launch

class MainViewModel(
    private val authenUc: AuthenUseCase
) : ViewModel() {
    private val _mainUISt = mutableStateOf(MainUIStateModel())
    val mainUISt: State<MainUIStateModel> = _mainUISt

    init {
//        getUserRole()
    }

    fun getUserRole() {
        val token = APP_TOKEN
        if (token.isEmpty()) {
            _mainUISt.value = onMainUIStateError()
            return
        }

        viewModelScope.launch {
            authenUc.getUserInfo("Bearer", token, deviceId = Build.DEVICE).collect() {
                when (it) {
                    is NetworkResult.Success -> {
                        val userRole = it.data?.department.orEmpty()
                        USER_ROLE = userRole

                        MAIN_MENU = when(USER_ROLE){
                            USER_ROLE_ADMIN -> ADMIN_OP_MENU
                            USER_ROLE_PACKING -> PACKING_OP_MENU
                            USER_ROLE_SHIPPING -> SHIPPING_OP_MENU
                            USER_ROLE_HOT_COIL -> HOT_COIL_OP_MENU
                            USER_ROLE_TEST_LAB -> TEST_LAB_OP_MENU
                            else ->{
                                if (USER_ROLE.contains(USER_ROLE_PRODUCTION)) PRODUCTION_OP_MENU
                                else DisplayHomeModel()
                            }
                        }

                        _mainUISt.value = onMainUIStateSuccess()

                    }
                    is NetworkResult.Loading -> onMainUIStateLoading()
                    is NetworkResult.Error -> {
                        _mainUISt.value = onMainUIStateError(
                            errorMsg = it.errorMessage // .getDisplay(getCurrentLanguage())
                        )
                    }
                    else -> initNavigateData() // Status Code = 204 or other
                }
            }
        }
    }


    fun logout() {
        val token = APP_TOKEN
        viewModelScope.launch {
            authenUc.logout("Bearer", token, deviceId = Build.DEVICE ).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        // Logout succeeded; update UI state and navigate to login
                        _mainUISt.value = onMainUIStateSuccess(
                            navigateType = MainNavigateType.GO_TO_RESTART_APP
                        )
                    }
                    is NetworkResult.Loading -> {
                        // Show loading state
                        onMainUIStateLoading()
                    }
                    is NetworkResult.Error -> {
                        // Show error message if logout failed
                        _mainUISt.value = onMainUIStateError(
                            errorMsg = result.errorMessage
                        )
                    }
                    else -> initNavigateData() // Status Code = 204 or other
                }
            }
        }
    }

    fun initNavigateData() {
        _mainUISt.value = onMainUIStateSuccess()
    }



    private fun onMainUIStateSuccess(
        navigateType: MainNavigateType = MainNavigateType.NONE,
        successMsg: String? = null
    ): MainUIStateModel = _mainUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onMainUIStateError(
        mainErrorType: MainErrorType = MainErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): MainUIStateModel = _mainUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = MainNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = MainErrorModel(
            mainErrorType = mainErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onMainUIStateLoading(isLoading: Boolean = true) {
        _mainUISt.value = _mainUISt.value.copy(isLoading = isLoading)
    }
}