package com.nssus.ihandy.ui.production.cvloading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.nssus.ihandy.model.production.cvloading.CVLoadingDialogAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseButton
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.production.cvloading.constant.CVLoadingConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.production.cvloading.constant.CVLoadingConstant.MAX_LENGTH_LCS
import com.nssus.ihandy.ui.production.cvloading.constant.CVLoadingConstant.MAX_LENGTH_SUPPLIER_NO
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CVLoadingScreen(
    uiCVLoadingSt: CVLoadingUIStateModel,
    // Use onAction only on screen
    onAction: (CVLoadingAction) -> Unit,
    // Use onDialogAction only route to view model
    //onDialogAction: (CVLoadingDialogAction) -> Unit
) {
    // Managing cursor focus for each text-field
    val coilNumberFocusRequest = FocusRequester()
    val lineSkidFocusRequester = FocusRequester()
    val supplierNumberFocusRequest = FocusRequester()

    // Preventing from the experimental APIs, under development not yet considered stable
    val keyboardController = LocalSoftwareKeyboardController.current

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiCVLoadingSt.coilNo)) }
    var lcsNoTxt by remember { mutableStateOf(TextFieldValue(uiCVLoadingSt.lcsNo)) }
    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(uiCVLoadingSt.supplierNo)) }

    // Using flag by to fast response with action
    LaunchedEffect(
        uiCVLoadingSt.isGetCoilNumberResp,
        uiCVLoadingSt.isGetLineSkidResp,
        uiCVLoadingSt.isGetSupplierNumberResp,
        uiCVLoadingSt.isClearAllTextFieldValue
    ) {
        when {
            uiCVLoadingSt.isGetCoilNumberResp -> {
                coilNoTxt = TextFieldValue()
                keyboardController?.hide()
                coilNumberFocusRequest.requestFocus()
                onAction(CVLoadingAction.SetInitFlagGetCoilNumberResp)
            }
            uiCVLoadingSt.isGetLineSkidResp -> {
                lcsNoTxt = TextFieldValue()
                keyboardController?.hide()
                lineSkidFocusRequester.requestFocus()
                onAction(CVLoadingAction.SetInitFlagGetLineSkidResp)
            }
            uiCVLoadingSt.isGetSupplierNumberResp -> {
                supplierNoTxt = TextFieldValue()
                keyboardController?.hide()
                supplierNumberFocusRequest.requestFocus()
                onAction(CVLoadingAction.SetInitFlagGetSupplierNumberResp)
            }
            uiCVLoadingSt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                lcsNoTxt = TextFieldValue()
                supplierNoTxt = TextFieldValue()
                coilNumberFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(CVLoadingAction.SetInitFlagClearAllTextField)
            }
        }
    }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(CVLoadingAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.cv_loading_menu_title)
            LazyColumn {
                item {
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.7f)
                                .focusRequester(coilNumberFocusRequest),
                            titleId = R.string.cv_loading_coil_no_title,
                            tfValue = coilNoTxt,
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            onTfValueChanged = {
                                coilNoTxt = it
                                onAction(CVLoadingAction.TypingCoilNoTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(CVLoadingAction.ClickNextActionCoilNoTextField)
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.3f)
                                .focusRequester(lineSkidFocusRequester),
                            titleId = R.string.cv_loading_lcs_title,
                            tfValue = lcsNoTxt,
                            tfMaxLength = MAX_LENGTH_LCS, //
                            onTfValueChanged = {
                                lcsNoTxt = it
                                onAction(CVLoadingAction.TypingLCSTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(CVLoadingAction.ClickNextActionLCSTextField)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        modifier = Modifier
                            .focusRequester(supplierNumberFocusRequest),
                        titleId = R.string.cv_loading_supplier_no_title,
                        tfValue = supplierNoTxt,
                        tfMaxLength = MAX_LENGTH_SUPPLIER_NO, //
                        onTfValueChanged = {
                            supplierNoTxt = it
                            onAction(CVLoadingAction.TypingSupplierNoTextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(CVLoadingAction.ClickNextActionSupplierNoTextField)
                        }
                    )
                    //Spacer(modifier = Modifier.height(Dimens.padding_inner_content_to_top_title_gray_value))
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    Row {
                        TitleAndGrayBgValueText(
                            modifier = Modifier
                                .weight(.6f),
                            titleTextStyle = FontStyles.txt20,
                            titleId = R.string.cv_loading_display_resd_title,
                            titleTextAlign = TextAlign.Start,
                            value = "${uiCVLoadingSt.coilNo} ${uiCVLoadingSt.lcsNo} ${uiCVLoadingSt.supplierNo}" ?: " ", //
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))

                        TitleAndGrayBgValueText(
                            modifier = Modifier
                                .weight(.4f),
                            titleTextStyle = FontStyles.txt20,
                            titleId = R.string.cv_loading_display_hc_days_title,
                            titleTextAlign = TextAlign.Start,
                            value = "${uiCVLoadingSt.coilNo} ${uiCVLoadingSt.lcsNo} ${uiCVLoadingSt.supplierNo}" ?: " ", //
                        )
                    }
                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.space_textfield_to_textfield,
                            bottom = Dimens.padding_below_bottom_title_gray_value
                        ),
                        titleTextStyle = FontStyles.txt20,
                        titleId = R.string.cv_loading_display_status_title,
                        titleTextAlign = TextAlign.Start,
                        value = "${uiCVLoadingSt.coilNo} ${uiCVLoadingSt.lcsNo} ${uiCVLoadingSt.supplierNo}" ?: " ", //
                    )
                }
            }
        }

//        RowPairButton(
//            textLeftButton = R.string.common_send_button,
//            onLeftButtonClick = { onDialogAction(CVLoadingDialogAction.ClickSendButton) },
//            leftModifier = Modifier.weight(.4f),
//            textRightButton = R.string.common_clear_all_button,
//            onRightButtonClick ={onDialogAction(CVLoadingDialogAction.ClickClearAllButton)},
//            rightModifier = Modifier.weight(.6f),
//            bottomSpace = 10.dp
//        )
        BaseButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            text = stringResource(id = R.string.common_clear_button),
            onButtonClick = {
                println("[Screen file : Click button clear from screen!]")
                onAction(CVLoadingAction.ClickClearAll)
            }
        )
        Spacer(modifier = Modifier.height(Dimens.space_bottom_content_card_to_button))
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun CVLoadingScreenPreview() {
    CVLoadingScreen(
        uiCVLoadingSt = CVLoadingUIStateModel
            (
            coilNo = "",
            lcsNo = "",
            supplierNo = "",
        ),
        onAction = {}
    )

}