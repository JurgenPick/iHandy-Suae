package com.nssus.ihandy.ui.common.shipbypro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.common.shipbypro.ShipByProAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProDialogAction
import com.nssus.ihandy.model.common.shipbypro.ShipByProUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseButton
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.common.shipbypro.constant.ShipByProConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.common.shipbypro.constant.ShipByProConstant.MAX_LENGTH_SHIPMENT_LOT
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShipByProScreen(
    uiShipByProSt : ShipByProUIStateModel,
    onAction: (ShipByProAction) -> Unit,
    onDialogAction: (ShipByProDialogAction) -> Unit
) {
    val shipmentLotFocusRequest = FocusRequester()
    val coilNumberFocusRequester = FocusRequester()

    val keyboardController = LocalSoftwareKeyboardController.current

    var shipmentLotTxt by remember { mutableStateOf(TextFieldValue(uiShipByProSt.shipmentLot)) }
    var coilNumberTxt by remember { mutableStateOf(TextFieldValue(uiShipByProSt.coilNumber)) }


    LaunchedEffect(
        uiShipByProSt.isGetShipmentLotRespSuccess,
        uiShipByProSt.isGetCoilNumberResp,
        uiShipByProSt.isClearAllTextFieldValue
    ) {
        when {
            uiShipByProSt.isGetShipmentLotRespSuccess -> {
                coilNumberFocusRequester.requestFocus()
                onAction(ShipByProAction.SetInitFlagGetShipmentLotRespSuccess)
            }
            uiShipByProSt.isGetCoilNumberResp -> {
                coilNumberTxt = TextFieldValue()
                coilNumberFocusRequester.requestFocus()
                onAction(ShipByProAction.SetInitFlagGetCoilNumberResp)
            }
            uiShipByProSt.isClearAllTextFieldValue -> {
                shipmentLotTxt = TextFieldValue()
                coilNumberTxt = TextFieldValue()
                shipmentLotFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(ShipByProAction.SetInitFlagClearAllTextField)
            }
        }

    }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(ShipByProAction.GoBack)}) {
        Column (modifier = Modifier
            .fillMaxHeight()
            .weight(1f)){
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.ship_by_pro_menu_title)
            LazyColumn {
                item {
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.6f)
                                .focusRequester(shipmentLotFocusRequest),
                            titleId = R.string.common_shipment_lot_table_title,
                            tfValue = shipmentLotTxt,
                            tfMaxLength = MAX_LENGTH_SHIPMENT_LOT, //
                            onTfValueChanged = {
                                shipmentLotTxt = it
                                onAction(ShipByProAction.TypingShipmentLotTextField(it.text))
                            }, onTfNextActionClick = {
                                onAction(ShipByProAction.ClickNextActionShipmentLotTextField)
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TitleAndGrayBgValueText(
                            modifier = Modifier.weight(.4f),
                            titleId = R.string.ship_by_pro_sum_weight_title,
                            value = if (uiShipByProSt.sumCoilWt == 0.0) " "
                                    else uiShipByProSt.sumCoilWt.toString(),
                            valueVerticalPadding = 1.5.dp
//                            ,
//                            valueTextStyle = FontStyles.txt20
                        )
                    }
                    Row(verticalAlignment = Alignment.Bottom){
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(.6f)
                                .focusRequester(coilNumberFocusRequester),
                            titleId = R.string.common_coil_no_table_title,
                            tfValue = coilNumberTxt,
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            onTfValueChanged = {
                                coilNumberTxt = it
                                onAction(ShipByProAction.TypingCoilNumberTextField(it.text))
                            }
                            , onTfNextActionClick = {
                                onAction(ShipByProAction.ClickNextActionCoilNumberTextField)
                            }
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        BaseButton(
                            modifier = Modifier
                                .offset( y = 3.dp)
//                                .padding(start = Dimens.space_textfield_to_textfield)
                                .padding(top = 45.dp)
//                                .height(40.dp)
                                .weight(.4f)
                            ,
//                            text = "Count : ${uiShipByProSt.resCoilList.count()}",
                            text = stringResource(id = R.string.ship_by_pro_count_coil, uiShipByProSt.resCoilList.count()),
                            textStyle = FontStyles.txt16,
                            enabled = uiShipByProSt.resCoilList.isEmpty().not(),
                            contentPadding = PaddingValues(0.dp),
                            onButtonClick = {onAction(ShipByProAction.ClickButtonCountCoil)}
                            )
                        println(uiShipByProSt.resCoilList.isEmpty().not())
                    }
                    Spacer(modifier = Modifier.padding(
                        top = Dimens.padding_below_bottom_title_gray_value)
                    )
                    Row {
                            TitleAndGrayBgValueText(
                                modifier = Modifier.weight(.6f),
                                titleTextStyle = FontStyles.txt20,
                                titleId = R.string.common_coil_no_table_title,
                                value = uiShipByProSt.lastCoil.ifEmpty { " " }
                            )

                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))


                            TitleAndGrayBgValueText(
                                modifier = Modifier.weight(.4f),
                                titleTextStyle = FontStyles.txt20,
                                titleId = R.string.ship_by_pro_coil_weight_title,
                                value = (if (uiShipByProSt.lastCoilWeight == 0.0 ) " "
                                else uiShipByProSt.lastCoilWeight.toString()
                                )
                            )
                    }
                    TitleAndGrayBgValueText(
                        titleTextStyle = FontStyles.txt20,
                        titleId = R.string.common_status_table_title,
                        value = uiShipByProSt.shipSt.ifEmpty { " " }
                    )
                }
            }
        }
        RowPairButton(
            textLeftButton = R.string.common_send_button,
            onLeftButtonClick = { onDialogAction(ShipByProDialogAction.ClickSendButton) },
            leftModifier = Modifier.weight(.4f),
            textRightButton = R.string.common_clear_all_button,
            onRightButtonClick ={onDialogAction(ShipByProDialogAction.ClickClearAllButton)},
            rightModifier = Modifier.weight(.6f),
            bottomSpace = 10.dp
            )


    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun ShipByProScreenPreview(){
    ShipByProScreen(
        uiShipByProSt = ShipByProUIStateModel(
            shipmentLot = "",
            coilNumber = ""
        ),
        onAction = {},
        onDialogAction = {}
    )

}


