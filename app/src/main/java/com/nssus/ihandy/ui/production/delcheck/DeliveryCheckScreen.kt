package com.nssus.ihandy.ui.production.delcheck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.production.cvloading.CVLoadingAction
import com.nssus.ihandy.model.production.cvloading.CVLoadingDialogAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckDialogAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckUIStateModel
import com.nssus.ihandy.ui.basecomposable.*
import com.nssus.ihandy.ui.production.delcheck.constant.DeliveryCheckConstant
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeliveryCheckScreen(
    uiDeliveryCheckSt: DeliveryCheckUIStateModel,
    onAction: (DeliveryCheckAction) -> Unit,
    onDialogAction: (DeliveryCheckDialogAction) -> Unit
) {
    // Managing cursor focus for each text-field
    val coilNumberFocusRequester = FocusRequester()

    // Preventing from the experimental APIs, under development not yet considered stable
    val keyboardController = LocalSoftwareKeyboardController.current

    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiDeliveryCheckSt.coilNo)) }

    // Using flag by to fast response with action
    LaunchedEffect(
        uiDeliveryCheckSt.isGetCoilNumberResp,
        uiDeliveryCheckSt.isClearAllTextFieldValue,
    ) {
        when {
            uiDeliveryCheckSt.isGetCoilNumberResp -> {
                coilNoTxt = TextFieldValue()
                keyboardController?.hide()
                coilNumberFocusRequester.requestFocus()
                onAction(DeliveryCheckAction.SetInitFlagGetCoilNumberResp)
            }
            uiDeliveryCheckSt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                coilNumberFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(DeliveryCheckAction.SetInitFlagClearAllTextField)
            }
        }
    }
    BaseContentCardWithBackButton(onBackIconClick = { onAction(DeliveryCheckAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Ensures the parent Column fills the available space
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.menu_del_check_title)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                item {
                    // Input Field for Coil Number
                    TopTitleAndBaseTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(coilNumberFocusRequester),
                        titleId = R.string.common_coil_no_table_title,
                        tfValue = coilNoTxt,
                        tfMaxLength = DeliveryCheckConstant.MAX_LENGTH_COIL_NO,
                        onTfValueChanged = {
                            coilNoTxt = it
                            onAction(DeliveryCheckAction.TypingCoilNumberTextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(DeliveryCheckAction.ClickNextActionCoilNoTextField)
                        }
                    )
                    Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                    // New Title for the Table
                    Text(
                        text = "Last 5 Coil No.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        style = FontStyles.txt14 //set text style same with apps
                    )
                    // Table Header
                    Table2ColumnHeaderRow(
                        firstTitleId = R.string.common_coil_no_table_title,
                        secondTitleId = R.string.common_status_table_title
                    )
                }
                // items list
                    items(uiDeliveryCheckSt.lastFiveCoils) { coilItem ->
                        Table2ColumnDetailRow(
                            isFirstColumnValueMatched = coilItem.isMatched,
                            firstColumnValue = coilItem.coilNumber,
                            secondColumnValue = coilItem.status,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Action Buttons
//            RowPairButton(
//                //textLeftButton = R.string.common_send_button,
//                onLeftButtonClick = { onDialogAction(DeliveryCheckDialogAction.ClickSendButton) },
//                leftModifier = Modifier.weight(.4f),
//                textRightButton = R.string.common_clear_all_button,
//                onRightButtonClick = { onDialogAction(DeliveryCheckDialogAction.ClickClearAllButton) },
//                rightModifier = Modifier.weight(.6f),
//                //bottomSpace = 10.dp
//            )
            BaseButton(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                text = stringResource(id = R.string.common_clear_button),
                onButtonClick = {
                    onDialogAction(DeliveryCheckDialogAction.ClickClearAllButton)
                }
            )
            Spacer(modifier = Modifier.height(Dimens.space_bottom_content_card_to_button))
        }
    }
@Preview(showBackground = true)
@Composable
fun DeliveryCheckScreenPreview() {
    DeliveryCheckScreen(
        uiDeliveryCheckSt = DeliveryCheckUIStateModel(
            coilNo = "",
            lastFiveCoils = listOf(
                DeliveryCheckUIStateModel.CoilItem("Coil11111", "Yes", true),
                DeliveryCheckUIStateModel.CoilItem("Coil22222", "No", false),
                DeliveryCheckUIStateModel.CoilItem("Coil33333", "No", false),
                DeliveryCheckUIStateModel.CoilItem("Coil44444", "No", false),
                DeliveryCheckUIStateModel.CoilItem("Coil55555", "Yes", true)
            )
        ),
        onAction = {},
        onDialogAction = {}
    )
}