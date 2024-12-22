package com.nssus.ihandy.ui.common.yardentry

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.yardentry.YardEntryAction
import com.nssus.ihandy.model.common.yardentry.YardEntryDialogAction
import com.nssus.ihandy.model.common.yardentry.YardEntryUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseButton
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.common.yardentry.constant.YardEntryConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.common.yardentry.constant.YardEntryConstant.MAX_LENGTH_SUPPLIER_NO
import com.nssus.ihandy.ui.common.yardentry.constant.YardEntryConstant.MAX_LENGTH_YYRRCCT
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun YardEntryScreen(
    uiYardEntrySt: YardEntryUIStateModel,
    onAction: (YardEntryAction) -> Unit,
    onDialogAction: (YardEntryDialogAction) -> Unit
) {

    val coilNoFocusRequester = FocusRequester()
    val yardFocusRequester = FocusRequester()
    val supplierNoFocusRequester = FocusRequester()

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.coilNo)) }
    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.yyrrcct)) }
    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(uiYardEntrySt.supplierNo)) }

    LaunchedEffect(
        uiYardEntrySt.isGetCoilSuccess,
        uiYardEntrySt.isGetCoilWithSupplierSuccess,
        uiYardEntrySt.isGetCoilFail,
        uiYardEntrySt.isGetSupplierNoSuccess,
        uiYardEntrySt.isGetSupplierNoFail,
        uiYardEntrySt.isGetYYRRCCTSuccess,
        uiYardEntrySt.isGetYYRRCCTFail,
        uiYardEntrySt.isClearAllTextFieldValue
    ) {
        when {
            uiYardEntrySt.isGetCoilSuccess -> {
                yardFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagGetCoilSuccess)
            }

            uiYardEntrySt.isGetCoilWithSupplierSuccess -> {
                supplierNoFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagGetCoilWithSupplierSuccess)
            }

            uiYardEntrySt.isGetCoilFail -> {
                coilNoTxt = TextFieldValue()
                coilNoFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagGetCoilFail)
            }
            uiYardEntrySt.isGetSupplierNoSuccess -> {
                yardFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagGetSupplierSuccess)
            }
            uiYardEntrySt.isGetSupplierNoFail -> {
                supplierNoTxt = TextFieldValue()
                supplierNoFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagGetSupplierFail)
            }
            uiYardEntrySt.isGetYYRRCCTSuccess -> {
                coilNoTxt = TextFieldValue()
                yyrrcctTxt = TextFieldValue(uiYardEntrySt.yyrrcct)
                onAction(YardEntryAction.SetInitFlagGetYYRRCCTSuccess)
            }
            uiYardEntrySt.isGetYYRRCCTFail -> {
                yyrrcctTxt = TextFieldValue()
                yardFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagGetYYRRCCTFail)
            }
            uiYardEntrySt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                yyrrcctTxt = TextFieldValue()
                supplierNoTxt = TextFieldValue()
                coilNoFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(YardEntryAction.SetInitFlagClearAllTextField)
            }
        }
    }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(YardEntryAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.menu_yard_entry_title)
            LazyColumn {
                item {
                    Row(verticalAlignment = Alignment.Bottom) {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.6f)
                                .focusRequester(coilNoFocusRequester),
                            titleId = R.string.common_coil_no_table_title,
                            tfIsAutoReplacePrefix = true,
                            tfValue = coilNoTxt,
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            onTfValueChanged = {
                                coilNoTxt = it
                                onAction(YardEntryAction.TypingCoilNoTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(YardEntryAction.ClickNextActionCoilTextField)
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.4f)
                                .focusRequester(yardFocusRequester),
                            titleId = R.string.common_yyrrcct_title,
                            titleStyle = FontStyles.txt22,
                            tfValue = yyrrcctTxt,
                            tfMaxLength = MAX_LENGTH_YYRRCCT, //
                            onTfValueChanged = {
                                yyrrcctTxt = it
                                onAction(YardEntryAction.TypingYYRRCCTTextField(it.text))
                            },
                            onTfNextActionClick = {
                                focusManager.clearFocus()
                                onAction(YardEntryAction.ClickNextActionYYRRCCTTextField)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        modifier = Modifier.focusRequester(supplierNoFocusRequester),
                        titleId = R.string.common_supplier_no_title,
                        tfIsEnabled = uiYardEntrySt.needSupplierNo,
                        tfValue = supplierNoTxt,
                        tfMaxLength = MAX_LENGTH_SUPPLIER_NO, //
                        onTfValueChanged = {
                            supplierNoTxt = it
                            onAction(YardEntryAction.TypingSupplierNoTextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(YardEntryAction.ClickNextActionSupplierNoTextField)
                        }
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.padding_inner_content_to_top_title_gray_value,
                            bottom = Dimens.padding_below_bottom_title_gray_value
                        ),
                        titleId = R.string.common_yyrrcct_title,
                        value = uiYardEntrySt.yardCurrent.ifEmpty { " " }, //
                    )
                }
            }
        }

        BaseButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            text = stringResource(id = R.string.common_clear_button),
            onButtonClick = {
                onDialogAction(YardEntryDialogAction.ClickClearAll)
            }
        )
        Spacer(modifier = Modifier.height(Dimens.space_bottom_content_card_to_button))
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun YardEntryScreenPreview() {
    YardEntryScreen(
        uiYardEntrySt = YardEntryUIStateModel
            (
            coilNo = "",
            yyrrcct = "",
            supplierNo = "",
        ),
        onAction = {},
        onDialogAction = {}
    )

}