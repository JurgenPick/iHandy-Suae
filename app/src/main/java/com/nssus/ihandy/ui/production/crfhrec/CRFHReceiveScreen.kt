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
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveAction
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveDialogAction
import com.nssus.ihandy.model.production.crfhrec.CRFHReceiveUIStateModel
import com.nssus.ihandy.model.production.cvloading.CVLoadingAction
import com.nssus.ihandy.model.production.delcheck.DeliveryCheckDialogAction
import com.nssus.ihandy.ui.basecomposable.*
import com.nssus.ihandy.ui.production.crfhrec.constant.CRFHReceiveConstant
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CRFHReceiveScreen(
    uiCRFHReceiveSt: CRFHReceiveUIStateModel,
    onAction: (CRFHReceiveAction) -> Unit,
    onDialogAction: (CRFHReceiveDialogAction) -> Unit
) {
    // Managing cursor focus for each text-field
    val coilNumberFocusRequester = FocusRequester()
    val yyrrccssFocusRequester = FocusRequester()
    val supplierNoFocusRequester = FocusRequester()

    // Preventing from the experimental APIs, under development not yet considered stable
    val keyboardController = LocalSoftwareKeyboardController.current
    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiCRFHReceiveSt.coilNo)) }
    var yyrrccssTxt by remember { mutableStateOf(TextFieldValue(uiCRFHReceiveSt.yyrrccss)) }
    var supplierNoTxt by remember { mutableStateOf(TextFieldValue(uiCRFHReceiveSt.supplierNo)) }

    // Using flag by to fast response with action
    LaunchedEffect(
        uiCRFHReceiveSt.isGetCoilNumberResp,
        uiCRFHReceiveSt.isGetYyrrccssResp,
        uiCRFHReceiveSt.isGetSupplierNumberResp,
        uiCRFHReceiveSt.isClearAllTextFieldValue
    ) {
        when {
            uiCRFHReceiveSt.isGetCoilNumberResp -> {
                coilNoTxt = TextFieldValue()
                keyboardController?.hide()
                coilNumberFocusRequester.requestFocus()
                onAction(CRFHReceiveAction.SetInitFlagGetCoilNumberResp)
            }
            uiCRFHReceiveSt.isGetYyrrccssResp -> {
                yyrrccssTxt = TextFieldValue()
                keyboardController?.hide()
                yyrrccssFocusRequester.requestFocus()
                onAction(CRFHReceiveAction.SetInitFlagGetYyrrccsdsResp)
            }
            uiCRFHReceiveSt.isGetSupplierNumberResp -> {
                supplierNoTxt = TextFieldValue()
                keyboardController?.hide()
                supplierNoFocusRequester.requestFocus()
                onAction(CRFHReceiveAction.SetInitFlagGetSupplierNumberResp)
            }
            uiCRFHReceiveSt.isClearAllTextFieldValue -> {
                coilNoTxt = TextFieldValue()
                yyrrccssTxt = TextFieldValue()
                supplierNoTxt = TextFieldValue()
                coilNumberFocusRequester.requestFocus()
                keyboardController?.hide()
                onAction(CRFHReceiveAction.SetInitFlagClearAllTextField)
            }
        }

    }
    BaseContentCardWithBackButton(onBackIconClick = { onAction(CRFHReceiveAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.crfh_receive_menu_title)
            LazyColumn {
                item {
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.53f)
                                .focusRequester(coilNumberFocusRequester),
                            titleId = R.string.common_coil_no_table_title,
                            tfValue = coilNoTxt,
                            tfMaxLength = CRFHReceiveConstant.MAX_LENGTH_COIL_NO,
                            onTfValueChanged = {
                                coilNoTxt = it
                                onAction(CRFHReceiveAction.TypingCoilNumberTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(CRFHReceiveAction.ClickNextActionCoilNoTextField)
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.47f)
                                .focusRequester(yyrrccssFocusRequester),
                            titleId = R.string.crfh_receive_yyrrcct_title,
                            tfValue = yyrrccssTxt,
                            tfMaxLength = CRFHReceiveConstant.MAX_LENGTH_YARD_NO,
                            onTfValueChanged = {
                                yyrrccssTxt = it
                                onAction(CRFHReceiveAction.TypingYYRRCCSSTextField(it.text))
                            },
                            onTfNextActionClick = {
                                onAction(CRFHReceiveAction.ClickNextActionYYRRCCSTextField)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(supplierNoFocusRequester),
                        titleId = R.string.crfh_receive_supplier_no_title,
                        tfValue = supplierNoTxt,
                        tfMaxLength = CRFHReceiveConstant.MAX_LENGTH_SUPPLIER_NO,
                        onTfValueChanged = {
                            supplierNoTxt = it
                            onAction(CRFHReceiveAction.TypingSupplierNoTextField(it.text))
                        },
                        onTfNextActionClick = {
                            onAction(CRFHReceiveAction.ClickNextActionSupplierNoTextField)
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.padding_inner_content_to_top_title_gray_value))
                    Row {
                        TitleAndGrayBgValueText(
                            //modifier = Modifier.weight(.7f),
                            titleTextStyle = FontStyles.txt20,
                            titleId = R.string.crfh_receive_yyrrcct_title,
                            value = uiCRFHReceiveSt.yyrrccss.ifEmpty { " " } ,
                            //titleTextAlign = TextAlign.Start
                        )
                        //Spacer(modifier = Modifier.weight(.3f))
                    }
                    TitleAndGrayBgValueText(
                        modifier = Modifier.fillMaxWidth(),
                        titleTextStyle = FontStyles.txt20,
                        titleId = R.string.crfh_receive_status_title,
                        value = uiCRFHReceiveSt.status.ifEmpty { " " },
                        //titleTextAlign = TextAlign.Start
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        BaseButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            text = stringResource(id = R.string.common_clear_button),
            onButtonClick = {
                onDialogAction(CRFHReceiveDialogAction.ClickClearAllButton)
            }
        )
        Spacer(modifier = Modifier.height(Dimens.space_bottom_content_card_to_button))
//        RowPairButton(
//            textLeftButton = R.string.common_send_button,
//            onLeftButtonClick = { onDialogAction(CRFHReceiveDialogAction.ClickSendButton) },
//            leftModifier = Modifier.weight(.4f),
//            textRightButton = R.string.common_clear_all_button,
//            onRightButtonClick = { onDialogAction(CRFHReceiveDialogAction.ClickClearAllButton) },
//            rightModifier = Modifier.weight(.6f),
//            bottomSpace = 10.dp
//        )
    }
}
@Preview(showBackground = true)
@Composable
fun CRFHReceiveScreenPreview() {
    CRFHReceiveScreen(
        uiCRFHReceiveSt = CRFHReceiveUIStateModel(
            coilNo = "",
            yyrrccss = "",
            supplierNo = "",
            status = ""
        ),
        onAction = {},
        onDialogAction = {}
    )
}