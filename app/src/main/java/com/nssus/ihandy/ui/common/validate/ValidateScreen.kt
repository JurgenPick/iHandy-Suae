package com.nssus.ihandy.ui.common.validate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.validate.ValidateAction
import com.nssus.ihandy.model.common.validate.ValidateUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.PrefixTitleAndBaseTextField
import com.nssus.ihandy.ui.basecomposable.PrefixTitleAndGrayBgValueTextWithIcon
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.common.validate.constant.ValidateConstant.MAX_LENGTH_VALIDATE1
import com.nssus.ihandy.ui.common.validate.constant.ValidateConstant.MAX_LENGTH_VALIDATE2
import com.nssus.ihandy.ui.common.validate.constant.ValidateConstant.MAX_LENGTH_VALIDATE3
import com.nssus.ihandy.ui.theme.Dimens

@Composable
fun ValidateScreen(
    uiValidateSt: ValidateUIStateModel,
    onAction: (ValidateAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val validate1FocusRequester = FocusRequester()
    val validate2FocusRequester = FocusRequester()
    val validate3FocusRequester = FocusRequester()

    var validateNo1Txt by remember { mutableStateOf(TextFieldValue(uiValidateSt.validateNo1)) }
    var validateNo2Txt by remember { mutableStateOf(TextFieldValue(uiValidateSt.validateNo2)) }
    var validateNo3Txt by remember { mutableStateOf(TextFieldValue(uiValidateSt.validateNo3)) }

    LaunchedEffect(
        uiValidateSt.isGetValidate1RespSuccess,
        uiValidateSt.isGetValidate2RespSuccess,
        uiValidateSt.isGetValidate3RespSuccess,
        uiValidateSt.isClearAllTextFieldValue
    ) {
        when {
            uiValidateSt.isGetValidate1RespSuccess -> {
                validate2FocusRequester.requestFocus() // set focus next textfield
                onAction(ValidateAction.SetInitFlagGetValidate1Resp) // action to clear flag
            }

            uiValidateSt.isGetValidate2RespSuccess -> {
                validate3FocusRequester.requestFocus() // set focus next textfield
                onAction(ValidateAction.SetInitFlagGetValidate2Resp) // action to clear flag
            }

            uiValidateSt.isGetValidate3RespSuccess -> {
                focusManager.clearFocus() //
                onAction(ValidateAction.SetInitFlagGetValidate3Resp) // action to clear flag
            }

            uiValidateSt.isClearAllTextFieldValue -> {
                validateNo1Txt = TextFieldValue()
                validateNo2Txt = TextFieldValue()
                validateNo3Txt = TextFieldValue()

                onAction(ValidateAction.SetInitFlagClearAllTextField)
            }
        }
    }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(ValidateAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.validate_menu_title)
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
                    PrefixTitleAndBaseTextField(
                        modifier = Modifier
                            .weight(.53f)
                            .focusRequester(validate1FocusRequester),
                        titleId = R.string.validate_no_title_1,
                        tfValue = validateNo1Txt,
                        tfMaxLength = MAX_LENGTH_VALIDATE1, //
                        tfIsError = uiValidateSt.isvalidateNo1TfError,
                        onTfValueChanged = {
                            validateNo1Txt = it
                            onAction(ValidateAction.TypingValidate1TextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(ValidateAction.ClickNextActionValidate1TextField)
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    PrefixTitleAndBaseTextField(
                        modifier = Modifier
                            .weight(.53f)
                            .focusRequester(validate2FocusRequester),
                        titleId = R.string.validate_no_title_2,
                        tfValue = validateNo2Txt,
                        tfMaxLength = MAX_LENGTH_VALIDATE2, //
                        tfIsError = uiValidateSt.isvalidateNo2TfError,
                        onTfValueChanged = {
                            validateNo2Txt = it
                            onAction(ValidateAction.TypingValidate2TextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(ValidateAction.ClickNextActionValidate2TextField)
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    PrefixTitleAndBaseTextField(
                        modifier = Modifier
                            .weight(.53f)
                            .focusRequester(validate3FocusRequester),
                        titleId = R.string.validate_no_title_3,
                        tfValue = validateNo3Txt,
                        tfMaxLength = MAX_LENGTH_VALIDATE3, //
                        tfIsError = uiValidateSt.isvalidateNo3TfError,
                        onTfValueChanged = {
                            validateNo3Txt = it
                            onAction(ValidateAction.TypingValidate3TextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(ValidateAction.ClickNextActionValidate3TextField)
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    PrefixTitleAndGrayBgValueTextWithIcon(
                        value = stringResource(
                            uiValidateSt.validateResultId ?: R.string.empty_string
                        ),
                        iconId = uiValidateSt.resultIconId
                    )
                }
            }
        }

        RowPairButton(
            textLeftButton = R.string.validate_check_btn,
            onLeftButtonClick = {
                onAction(ValidateAction.ClickSendButton)
            },
            onRightButtonClick = {
                onAction(ValidateAction.ClearAllValueButton)
            }
        )
    }
}


@Preview(showBackground = true, locale = "th")
@Composable
fun ValidateScreenPreview() {
    ValidateScreen(
        uiValidateSt = ValidateUIStateModel(
            resultIconId = R.drawable.ic_dialog_green_tick,
            validateNo1 = "",
            validateNo2 = "",
            validateNo3 = "",
        ),
        onAction = {}
    )
}