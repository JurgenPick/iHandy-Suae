package com.nssus.ihandy.ui.shipping.pcshipping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.getMatchedItemWithCoilNumber
import com.nssus.ihandy.data.extension.isNotNull
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingAction
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingDialogAction
import com.nssus.ihandy.model.shipping.pcshipping.PcShippingUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.Table3ColumnDetailRow
import com.nssus.ihandy.ui.basecomposable.Table3ColumnHeaderRow
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.MAX_LENGTH_SHIPMENT_LOT
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.MAX_LENGTH_TRUCK
import com.nssus.ihandy.ui.shipping.pcshipping.constant.PcShippingConstant.MAX_LENGTH_WIDTH
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.SilverGray


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PcShippingScreen(
    uiPcShippingSt : PcShippingUIStateModel,
    onAction: (PcShippingAction) -> Unit,
    onDialogAction: (PcShippingDialogAction) -> Unit
){
    val shipmentLotFocusRequest = FocusRequester()
    val truckNumberFocusRequest = FocusRequester()
    val coilNumberFocusRequest = FocusRequester()
    val widthFocusRequest = FocusRequester()

    val keyboardController = LocalSoftwareKeyboardController.current

//    val focusDisplay = uiPcShippingSt.resListCoil.getMatchedItem()
    val focusedCoilNoItem = uiPcShippingSt.resListCoil.getMatchedItemWithCoilNumber(uiPcShippingSt.coilNumber)
//    val focusedCoilNoItem = uiPcShippingSt.resListCoil.getMatchedCoilNumber(uiPcShippingSt.coilNumber)

    val listState = rememberLazyListState()

    var shipmentLotTxt by remember { mutableStateOf(TextFieldValue(uiPcShippingSt.shipmentLot)) }
    var truckNumberTxt by remember { mutableStateOf(TextFieldValue(uiPcShippingSt.truck)) }
    var coilNumberTxt by remember { mutableStateOf(TextFieldValue(uiPcShippingSt.coilNumber)) }
    var widthTxt by remember { mutableStateOf(TextFieldValue(uiPcShippingSt.width.toString())) }

    LaunchedEffect(
        uiPcShippingSt.isGetShipmentLotSuccess,
        uiPcShippingSt.isGetTruckSuccess,
        uiPcShippingSt.isGetTruckFail,
        uiPcShippingSt.isGetCoilResp,
        uiPcShippingSt.isGetCoilRespWithNeedWidth,
        uiPcShippingSt.isClearAllTextField,
        uiPcShippingSt.isGetWidthFail,
        uiPcShippingSt.isGetWidthSuccess,
        focusedCoilNoItem
    ) {
        when{
            uiPcShippingSt.isGetShipmentLotSuccess -> {
                truckNumberFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(PcShippingAction.SetInitFlagGetShipmentLotSuccess)
            }
            uiPcShippingSt.isGetTruckSuccess -> {
                truckNumberTxt = TextFieldValue(text = uiPcShippingSt.truck)
                coilNumberFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(PcShippingAction.SetInitFlagGetTruckSuccess)
            }
            uiPcShippingSt.isGetTruckFail -> {
                truckNumberTxt = TextFieldValue()
                coilNumberTxt = TextFieldValue()
                truckNumberFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(PcShippingAction.SetInitFlagGetTruckFail)
            }
            uiPcShippingSt.isGetCoilResp -> {
                coilNumberTxt = TextFieldValue()
                coilNumberFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(PcShippingAction.SetInitGetCoilResp)
            }
            uiPcShippingSt.isGetCoilRespWithNeedWidth -> {
                widthFocusRequest.requestFocus()
                keyboardController?.show()
                onAction(PcShippingAction.SetInitGetCoilRespWithNeedWidth)
            }
            uiPcShippingSt.isGetWidthSuccess -> {
                coilNumberTxt = TextFieldValue()
                widthTxt = TextFieldValue()
                coilNumberFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(PcShippingAction.SetInitGetWidthSuccess)
            }
            uiPcShippingSt.isGetWidthFail -> {
                widthTxt = TextFieldValue()
                widthFocusRequest.requestFocus()
                keyboardController?.show()
                onAction(PcShippingAction.SetInitGetWidthFail)
            }
            uiPcShippingSt.isClearAllTextField -> {
                shipmentLotTxt = TextFieldValue()
                truckNumberTxt = TextFieldValue()
                coilNumberTxt = TextFieldValue()
                widthTxt = TextFieldValue()
                shipmentLotFocusRequest.requestFocus()
                keyboardController?.hide()
                onAction(PcShippingAction.SetInitFlagClearAllTextField)
            }
            focusedCoilNoItem.isNotNull() ->{
                listState.animateScrollToItem(uiPcShippingSt.resListCoil.indexOf(focusedCoilNoItem))
            }
        }
    }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(PcShippingAction.GoBack) }) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.menu_pc_shipping_title)
            Column {
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(6f)
                                .focusRequester(shipmentLotFocusRequest),
                            titleId = R.string.common_shipment_lot_table_title,
                            tfMaxLength = MAX_LENGTH_SHIPMENT_LOT,
                            tfValue = shipmentLotTxt,
                            onTfValueChanged = {
                                shipmentLotTxt = it
                                onAction(PcShippingAction.TypingShipmentLotTextField(it.text))
                            },
                            onTfNextActionClick = { onAction(PcShippingAction.ClickNextActionShipmentLot)})
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(4f)
                                .focusRequester(truckNumberFocusRequest),
                            titleId = R.string.shipping_truck_title,
                            tfMaxLength = MAX_LENGTH_TRUCK,
                            tfValue = truckNumberTxt,
                            onTfValueChanged ={
                                truckNumberTxt = it
                                onAction(PcShippingAction.TypingTruckTextField(it.text))
                            } ,
                            onTfNextActionClick = { onAction(PcShippingAction.ClickNextActionTruck) })
                    }
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(6f)
                                .focusRequester(coilNumberFocusRequest),
                            titleId = R.string.common_coil_no_table_title,
                            tfMaxLength = MAX_LENGTH_COIL_NO,
                            tfValue = coilNumberTxt,
                            onTfValueChanged = {
                                coilNumberTxt = it
                                onAction(PcShippingAction.TypingCoilNumberTextField(it.text))
                            },
                            onTfNextActionClick = { onAction(PcShippingAction.ClickNextActionCoilNumber) })
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier
                                .weight(4f)
                                .focusRequester(widthFocusRequest),
                            titleId = R.string.common_width_title,
                            tfIsEnabled = uiPcShippingSt.isEnabledWidth,
                            tfMaxLength = MAX_LENGTH_WIDTH,
                            tfValue = (if(uiPcShippingSt.width == 0.0) TextFieldValue()
                            else widthTxt),
                            onTfValueChanged = {
                                widthTxt = it
                                onAction(PcShippingAction.TypingWidthTextField(it.text))
                            } ,
                            onTfNextActionClick = { onAction(PcShippingAction.ClickNextActionWidth) })
                    }
                Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                Table3ColumnHeaderRow()
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SilverGray)
                    .padding(horizontal = 10.dp),
                state = listState
            ) {
                items(uiPcShippingSt.resListCoil){
                    Table3ColumnDetailRow(
                        isFirstColumnValueMatched = it.isMatched ,
                        firstColumnValue = it.getCoilListResponse.coilNumber ?: " ",
                        secondColumnValue = it.getCoilListResponse.shipStatus ?: " ",
                        thirdColumnValue = it.getCoilListResponse.dock ?: " "
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        RowPairButton(
            textLeftButton = R.string.common_send_button,
            leftModifier = Modifier.weight(.4f),
            onLeftButtonClick = { onDialogAction(PcShippingDialogAction.ClickSendButton) },
            textRightButton = R.string.common_clear_all_button,
            rightModifier = Modifier.weight(.6f),
            onRightButtonClick ={onDialogAction(PcShippingDialogAction.ClickClearAllButton)},
            bottomSpace = 10.dp
        )

    }
}