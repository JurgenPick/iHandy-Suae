package com.nssus.ihandy.ui.shipping.chargerelo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.nssus.ihandy.R
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloAction
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloUIStateModel
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.shipping.chargerelo.constant.ChargeReloConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.shipping.chargerelo.constant.ChargeReloConstant.MAX_LENGTH_WIDTH
import com.nssus.ihandy.ui.shipping.chargerelo.constant.ChargeReloConstant.MAX_LENGTH_YYRRCCT
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@Composable
fun ChargeReloScreen(
    uiChargeReloSt: ChargeReloUIStateModel,
//    coilNo: String,
//    yyrrcct: String,
//    width: String,
    onAction: (ChargeReloAction) -> Unit
) {
    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiChargeReloSt.coilNo)) }
    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(uiChargeReloSt.yyrrcct)) }
    var widthTxt by remember { mutableStateOf(TextFieldValue(uiChargeReloSt.width)) }
    BaseContentCardWithBackButton(onBackIconClick = { onAction(ChargeReloAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.charge_relo_menu_title)
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier.weight(.53f),
                            titleId = R.string.charge_relo_coil_no_title,
                            tfValue = coilNoTxt,
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            onTfValueChanged = {
                                coilNoTxt = it
                                onAction(ChargeReloAction.TypingCoilNoTextField(it.text))
                            },
                            onTfNextActionClick = {}
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier.weight(.47f),
                            titleId = R.string.charge_relo_yyrrcct_title,
                            tfValue = yyrrcctTxt,
                            tfMaxLength = MAX_LENGTH_YYRRCCT, //
                            onTfValueChanged = {
                                yyrrcctTxt = it
                                onAction(ChargeReloAction.TypingYYRRCCTTextField(it.text))
                            },
                            onTfNextActionClick = {}
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        titleId = R.string.common_width_title,
                        tfValue = widthTxt,
                        tfMaxLength = MAX_LENGTH_WIDTH, // tfMaxLine = 2,
                        onTfValueChanged = {
                            widthTxt = it
                            onAction(ChargeReloAction.TypingWidthNoTextField(it.text))
                        },
                        onTfNextActionClick = {}
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.padding_inner_content_to_top_title_gray_value_2
                        ),
                        titleId = R.string.charge_relo_display_coil_no_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "${uiChargeReloSt.coilNo}", //
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.space_textfield_to_textfield,
                            bottom = Dimens.padding_below_bottom_title_gray_value
                        ),
                        titleId = R.string.charge_relo_display_yyrrcct_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "${uiChargeReloSt.yyrrcct}", //
                    )
                }
            }
        }

        RowPairButton(
            onLeftButtonClick = { onAction(ChargeReloAction.ClickSendButton) },
            onRightButtonClick = {
                coilNoTxt = TextFieldValue()
                yyrrcctTxt = TextFieldValue()
                widthTxt = TextFieldValue()

                onAction(ChargeReloAction.ClickClearButton)
            }
        )
    }
}


@Preview(showBackground = true, locale = "th")
@Composable
fun ChargeReloScreenScreenPreview() {
    ChargeReloScreen(
//        onAction = {}
        uiChargeReloSt = ChargeReloUIStateModel(
            coilNo = "",
            yyrrcct = "",
            width = "",
        ),
        onAction = {}
    )
}