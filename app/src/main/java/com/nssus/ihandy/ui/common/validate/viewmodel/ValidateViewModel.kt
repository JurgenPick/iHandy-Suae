package com.nssus.ihandy.ui.common.validate.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.isEqualsMaxLength
import com.nssus.ihandy.data.extension.isErrorTextFieldWith
import com.nssus.ihandy.data.extension.isNotEqualsMaxLength
import com.nssus.ihandy.model.common.validate.ValidateAction
import com.nssus.ihandy.model.common.validate.ValidateErrorModel
import com.nssus.ihandy.model.common.validate.ValidateErrorType
import com.nssus.ihandy.model.common.validate.ValidateNavigateType
import com.nssus.ihandy.model.common.validate.ValidateUIStateModel
import com.nssus.ihandy.ui.common.validate.constant.ValidateConstant.MAX_LENGTH_VALIDATE1
import com.nssus.ihandy.ui.common.validate.constant.ValidateConstant.MAX_LENGTH_VALIDATE2
import com.nssus.ihandy.ui.common.validate.constant.ValidateConstant.MAX_LENGTH_VALIDATE3

class ValidateViewModel(

) : ViewModel() {
    private val _validateUISt = mutableStateOf(ValidateUIStateModel())
    val validateUISt: State<ValidateUIStateModel> = _validateUISt

    fun action(viewAction: ValidateAction) {
        when (viewAction) {
            is ValidateAction.GoBack -> {
                _validateUISt.value = ValidateUIStateModel(isClearAllTextFieldValue = true)
                _validateUISt.value = onValidateUIStateSuccess(
                    navigateType = ValidateNavigateType.GO_BACK
                )
            }

            is ValidateAction.TypingValidate1TextField -> { //
//                onValidateUIStateLoading()
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    validateNo1 = viewAction.text,
                    isvalidateNo1TfError = viewAction.text.isErrorTextFieldWith(
                        MAX_LENGTH_VALIDATE1
                    ),
                )
                println("SSSS1: ${_validateUISt.value.validateNo1} ${_validateUISt.value.validateNo2} ${_validateUISt.value.validateNo3}")
            }

            is ValidateAction.ClickNextActionValidate1TextField -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isGetValidate1RespSuccess = true
                )
            }

            is ValidateAction.SetInitFlagGetValidate1Resp -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isGetValidate1RespSuccess = false
                )
            }

            is ValidateAction.TypingValidate2TextField -> { //
//                onValidateUIStateLoading()
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    validateNo2 = viewAction.text,
                    isvalidateNo2TfError = viewAction.text.isErrorTextFieldWith(
                        MAX_LENGTH_VALIDATE2
                    ),
                )
                println("SSSS2: ${_validateUISt.value.validateNo1} ${_validateUISt.value.validateNo2} ${_validateUISt.value.validateNo3}")
            }

            is ValidateAction.ClickNextActionValidate2TextField -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isGetValidate2RespSuccess = true
                )
            }

            is ValidateAction.SetInitFlagGetValidate2Resp -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isGetValidate2RespSuccess = false
                )
            }

            is ValidateAction.TypingValidate3TextField -> { //
//                onValidateUIStateLoading()
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    validateNo3 = viewAction.text,
                    isvalidateNo3TfError = viewAction.text.isErrorTextFieldWith(
                        MAX_LENGTH_VALIDATE3
                    ),
                )
                println("SSSS3: ${_validateUISt.value.validateNo1} ${_validateUISt.value.validateNo2} ${_validateUISt.value.validateNo3}")
            }

            is ValidateAction.ClickNextActionValidate3TextField -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isGetValidate3RespSuccess = true
                )
            }

            is ValidateAction.SetInitFlagGetValidate3Resp -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isGetValidate3RespSuccess = false
                )
            }

            is ValidateAction.ClickSendButton -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    validateResultId = when {
                        _validateUISt.value.validateNo1.isEmpty() || _validateUISt.value.validateNo2.isEmpty() || _validateUISt.value.validateNo3.isEmpty() -> {
                            R.string.common_ng_text
                        }

                        _validateUISt.value.validateNo1.isNotEqualsMaxLength(MAX_LENGTH_VALIDATE1) || _validateUISt.value.validateNo2.isNotEqualsMaxLength(
                            MAX_LENGTH_VALIDATE2
                        ) || _validateUISt.value.validateNo3.isNotEqualsMaxLength(
                            MAX_LENGTH_VALIDATE3
                        ) -> {
                            R.string.common_ng_text
                        }

                        _validateUISt.value.validateNo1 == _validateUISt.value.validateNo2 && _validateUISt.value.validateNo2 == _validateUISt.value.validateNo3 -> {
                            R.string.common_ok_text
                        }

                        else -> R.string.common_ng_text
                    },
                    resultIconId = when {
                        _validateUISt.value.validateNo1.isEmpty() || _validateUISt.value.validateNo2.isEmpty() || _validateUISt.value.validateNo3.isEmpty() -> {
                            R.drawable.ic_dialog_red_cross
                        }

                        _validateUISt.value.validateNo1.isNotEqualsMaxLength(MAX_LENGTH_VALIDATE1) || _validateUISt.value.validateNo2.isNotEqualsMaxLength(
                            MAX_LENGTH_VALIDATE2
                        ) || _validateUISt.value.validateNo3.isNotEqualsMaxLength(
                            MAX_LENGTH_VALIDATE3
                        ) -> {
                            R.drawable.ic_dialog_red_cross
                        }

                        _validateUISt.value.validateNo1 == _validateUISt.value.validateNo2 && _validateUISt.value.validateNo2 == _validateUISt.value.validateNo3 -> {
                            R.drawable.ic_dialog_green_tick
                        }

                        else -> R.drawable.ic_dialog_red_cross
                    },
                )
                println("SSSSsend: ${_validateUISt.value.validateNo1} ${_validateUISt.value.validateNo2} ${_validateUISt.value.validateNo3}")
            }

            is ValidateAction.ClearAllValueButton -> {
                _validateUISt.value =
                    ValidateUIStateModel(isClearAllTextFieldValue = true) // clear and then .copy( set some values to update (optional))
            }

            is ValidateAction.SetInitFlagClearAllTextField -> {
                _validateUISt.value = onValidateUIStateSuccess().copy(
                    isClearAllTextFieldValue = false
                )
            }
        }
    }

    fun initNavigateData() {
        _validateUISt.value = onValidateUIStateSuccess()
    }

    private fun onValidateUIStateSuccess(
        navigateType: ValidateNavigateType = ValidateNavigateType.NONE,
        successMsg: String? = null
    ): ValidateUIStateModel = _validateUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onValidateUIStateError(
        validateErrorType: ValidateErrorType = ValidateErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): ValidateUIStateModel = _validateUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = ValidateNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = ValidateErrorModel(
            validateErrorType = validateErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onValidateUIStateLoading(isLoading: Boolean = true) {
        _validateUISt.value = _validateUISt.value.copy(isLoading = isLoading)
    }
}