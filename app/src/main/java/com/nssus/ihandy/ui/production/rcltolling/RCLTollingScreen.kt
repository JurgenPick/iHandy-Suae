package com.nssus.ihandy.ui.production.rcltolling

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingDialogAction
import com.nssus.ihandy.model.production.rcltolling.RCLTollingUIStateModel
import com.nssus.ihandy.ui.basecomposable.*
import com.nssus.ihandy.ui.production.rcltolling.constant.RCLTollingConstant
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RCLTollingScreen(
    uiRCLTollingSt: RCLTollingUIStateModel,
    onAction: (RCLTollingAction) -> Unit,
    onDialogAction: (RCLTollingDialogAction) -> Unit
) {
    // Managing cursor focus for each text-field
    val coilNumberFocusRequester = FocusRequester()

    // Preventing from the experimental APIs, under development not yet considered stable
    val keyboardController = LocalSoftwareKeyboardController.current

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiRCLTollingSt.coilNo)) }

    // Using flag by to fast response with action
    LaunchedEffect(
        uiRCLTollingSt.isGetCoilNumberResp,
        uiRCLTollingSt.isClearAllTextFieldValue
    ) {
        when {
            uiRCLTollingSt.isGetCoilNumberResp -> {
                coilNoTxt = TextFieldValue()
                keyboardController?.hide()
                coilNumberFocusRequester.requestFocus()
                onAction(RCLTollingAction.SetInitFlagGetCoilNumberResp)
            }
            uiRCLTollingSt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                coilNumberFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(RCLTollingAction.SetInitFlagClearAllTextField)
            }
        }

    }
    BaseContentCardWithBackButton(onBackIconClick = { onAction(RCLTollingAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.menu_rcl_tolling_title)
            LazyColumn {
                item {
                    TopTitleAndBaseTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(coilNumberFocusRequester),
                        titleId = R.string.common_coil_no_table_title,
                        tfValue = coilNoTxt,
                        tfMaxLength = RCLTollingConstant.MAX_LENGTH_COIL_NO,
                        onTfValueChanged = {
                            coilNoTxt = it
                            onAction(RCLTollingAction.TypingCoilNumberTextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(RCLTollingAction.ClickNextActionCoilNoTextField)
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.padding_inner_content_to_top_title_gray_value))
                        TitleAndGrayBgValueText(
                            //modifier = Modifier.fillMaxWidth(),
                            titleTextStyle = FontStyles.txt22,
                            titleId = R.string.rcl_tolling_cancel_return_title,
                            value = uiRCLTollingSt.cancelReturn.ifEmpty { " " },
                            //titleTextAlign = TextAlign.Start
                        )
                    TitleAndGrayBgValueText(
                        //modifier = Modifier.fillMaxWidth(),
                        titleTextStyle = FontStyles.txt22,
                        titleId = R.string.rcl_tolling_yyrrcct_title,
                        value = uiRCLTollingSt.yyrrccss.ifEmpty { " " },
                        //titleTextAlign = TextAlign.Start
                    )
                }
            }
        }
        BaseButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            text = stringResource(id = R.string.common_clear_button),
            onButtonClick = {
                onDialogAction(RCLTollingDialogAction.ClickClearAllButton)
            }
        )
        Spacer(modifier = Modifier.height(Dimens.space_bottom_content_card_to_button))
//        RowPairButton(
//            textLeftButton = R.string.common_send_button,
//            onLeftButtonClick = { onDialogAction(RCLTollingDialogAction.ClickSendButton) },
//            leftModifier = Modifier.weight(.4f),
//            textRightButton = R.string.common_clear_all_button,
//            onRightButtonClick = { onDialogAction(RCLTollingDialogAction.ClickClearAllButton) },
//            rightModifier = Modifier.weight(.6f),
//            bottomSpace = 10.dp
//        )
    }
}
@Preview(showBackground = true)
@Composable
fun RCLTollingScreenPreview() {
    RCLTollingScreen(
        uiRCLTollingSt = RCLTollingUIStateModel(
            coilNo = "",
            cancelReturn = "",
            yyrrccss = ""
        ),
        onAction = {},
        onDialogAction = {}
    )
}