package com.nssus.ihandy.ui.login.viewmodel

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.constant.AppConstant
import com.nssus.ihandy.data.usecase.AuthenUseCase
import com.nssus.ihandy.model.authen.LoginRequest
import com.nssus.ihandy.model.login.LoginAction
import com.nssus.ihandy.model.login.LoginErrorModel
import com.nssus.ihandy.model.login.LoginErrorType
import com.nssus.ihandy.model.login.LoginNavigateType
import com.nssus.ihandy.model.login.LoginUIStateModel
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.launch

class LoginViewModel(
//    private val sharedPref: SharedPreferences,
//    private val homeUc: HomeUseCase, // un comment this
    private val authenUc: AuthenUseCase // comment this if not use
) : ViewModel() {
    private val _loginUISt = mutableStateOf(LoginUIStateModel())
    val loginUISt: State<LoginUIStateModel> = _loginUISt

    fun action(viewAction: LoginAction) {
        when (viewAction) {
            is LoginAction.ClickLoginButton -> login(viewAction.username, viewAction.password)
            is LoginAction.ClickNextUserName -> {
                println("NextUsername")
                _loginUISt.value = LoginUIStateModel().copy(
                    isNextUserNameAction = true
                )
            }
            is LoginAction.ClickNextPassword -> {
                println("NextPassword")
                _loginUISt.value = LoginUIStateModel().copy(
                    isNextPasswordAction = true
                )
            }
            is LoginAction.SetInitFlagUsername ->{
                _loginUISt.value = LoginUIStateModel().copy(
                    isNextUserNameAction = false
                )
            }
            is LoginAction.SetInitFlagPassword ->{
                _loginUISt.value = LoginUIStateModel().copy(
                    isNextPasswordAction = false
                )
            }
            is LoginAction.SetInitClearTextFiled ->{
                _loginUISt.value = LoginUIStateModel(isClearTextField = false)
            }
            is LoginAction.ClickOKDialog ->{
                when(_loginUISt.value.errorBody?.loginErrorType){
                    LoginErrorType.ERROR_FROM_API ->{
                        _loginUISt.value = LoginUIStateModel(
                            isClearTextField = true
                        )
                    }
                    else ->{action(LoginAction.InitNavigateData)}
                }
            }
            is LoginAction.InitNavigateData -> initNavigateData()
            else ->{}
        }
    }

    private fun login(username:String, password: String) {
        println("Text login")
        if (username.isEmpty()) {
            _loginUISt.value = onLoginUIStateError(
                loginErrorType = LoginErrorType.EMPTY_FILL_USERNAME,
            )
            return
        }
        if (password.isEmpty()) {
            _loginUISt.value = onLoginUIStateError(
                loginErrorType = LoginErrorType.EMPTY_FILL_PASSWORD,
            )
            return
        }

        val loginRequest = LoginRequest(
            employeeId = username.toInt(),/*Todo Bug*/
            password = password,
            deviceId = Build.DEVICE
        )
        viewModelScope.launch {
            authenUc.login(loginRequest).collect {
                when (it) {
                    is NetworkResult.Success -> {
                        println("Login result: $it")
                        // Store the access token after login success
                        AppConstant.APP_TOKEN = it.data?.accessToken.orEmpty()
                        // Store the refresh token (generating when login success)
                        AppConstant.REFRESH_TOKEN = it.data?.refreshToken.orEmpty()
                        AppConstant.USERNAME = username


                        // print loop for value
//                        it.data?.forEach {
//                            println("NAME: ${it.message}")
//                            println("AVATAR: ${it.result}")
//                            println("ID: ${it.id}")
//                            println("----------------------------------------")

//                        }
                        _loginUISt.value = onLoginUIStateSuccess(
                            navigateType = LoginNavigateType.GO_TO_MAIN,
                            successMsg = "$username $password" //
                        )
                    }

                    is NetworkResult.Loading -> onLoginUIStateLoading()
                    is NetworkResult.Error -> {
                        _loginUISt.value = onLoginUIStateError(
                            errorMsg = it.errorMessage // .getDisplay(getCurrentLanguage())
                        )
                    }
                }
            }
        }
        // login bypass ti main screen
//        USER_ROLE = if (username.isEmpty()) USER_ROLE_PACKING else USER_ROLE_SHIPPING
//        _loginUISt.value = onLoginUIStateSuccess(
//            navigateType = LoginNavigateType.GO_TO_MAIN,
//            successMsg = "$username $password" //
//        )
    }

    fun initNavigateData() {
        _loginUISt.value = onLoginUIStateSuccess()
    }

    private fun onLoginUIStateSuccess(
        navigateType: LoginNavigateType = LoginNavigateType.NONE,
        successMsg: String? = null
    ): LoginUIStateModel = _loginUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onLoginUIStateError(
        loginErrorType: LoginErrorType = LoginErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): LoginUIStateModel = _loginUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = LoginNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = LoginErrorModel(
            loginErrorType = loginErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onLoginUIStateLoading(isLoading: Boolean = true) {
        _loginUISt.value = _loginUISt.value.copy(isLoading = isLoading)
    }

}