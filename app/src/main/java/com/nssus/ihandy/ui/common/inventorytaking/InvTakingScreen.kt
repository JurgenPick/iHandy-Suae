package com.nssus.ihandy.ui.common.inventorytaking

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
import com.nssus.ihandy.model.common.inventorytaking.InvTakingAction
import com.nssus.ihandy.model.common.inventorytaking.InvTakingUIStateModel

import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.common.inventorytaking.constant.InvTakingConstant.MAX_LENGTH_COIL_NO
import com.nssus.ihandy.ui.common.inventorytaking.constant.InvTakingConstant.MAX_LENGTH_SUPPLIER_NO
import com.nssus.ihandy.ui.common.inventorytaking.constant.InvTakingConstant.MAX_LENGTH_YYRRCCT

import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles


@Composable
fun InvTakingScreen(
    uiInvTakingSt: InvTakingUIStateModel,
    onAction: (InvTakingAction) -> Unit

) {
    var coilNoTxt by remember { mutableStateOf(TextFieldValue(uiInvTakingSt.coilNo)) }
    var yyrrcctTxt by remember { mutableStateOf(TextFieldValue(uiInvTakingSt.yyrrcct)) }
    var supplierTxt by remember { mutableStateOf(TextFieldValue(uiInvTakingSt.supplier)) }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(InvTakingAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.inv_taking_menu_title)
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
                    Row {
                        TopTitleAndBaseTextField(
                            modifier = Modifier.weight(.53f),
                            titleId = R.string.common_yyrrcct_title,
                            tfValue = yyrrcctTxt,
                            tfMaxLength = MAX_LENGTH_YYRRCCT, //
                            onTfValueChanged = {
                                yyrrcctTxt = it
                                onAction(InvTakingAction.TypingYYRRCCTTextField(it.text))
                            },
                            onTfNextActionClick = {}
                        )
                        Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                        TopTitleAndBaseTextField(
                            modifier = Modifier.weight(.47f),
                            titleId = R.string.common_coil_no_table_title,
                            tfValue = coilNoTxt,
                            tfMaxLength = MAX_LENGTH_COIL_NO, //
                            onTfValueChanged = {
                                coilNoTxt = it
                                onAction(InvTakingAction.TypingCoilNoTextField(it.text))
                            },
                            onTfNextActionClick = {}
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(
                        titleId = R.string.inv_taking_supplier_no_title,
                        tfValue = supplierTxt,
                        tfMaxLength = MAX_LENGTH_SUPPLIER_NO, // tfMaxLine = 2,
                        onTfValueChanged = {
                            supplierTxt = it
                            onAction(InvTakingAction.TypingSupplierNoTextField(it.text))
                        },
                        onTfNextActionClick = {}
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.padding_inner_content_to_top_title_gray_value
                        ),
                        titleId = R.string.inv_taking_display_lasted_coil_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "${uiInvTakingSt.coilNo}", //
                    )


                }
            }
        }

        RowPairButton(
            onLeftButtonClick = { onAction(InvTakingAction.ClickSendButton) },
            onRightButtonClick = {
                coilNoTxt = TextFieldValue()
                yyrrcctTxt = TextFieldValue()
                supplierTxt = TextFieldValue()

                onAction(InvTakingAction.ClickClearButton)
            }
        )
    }
}

    @Preview(showBackground = true, locale = "th")
    @Composable
    fun InvTakingScreenPreview() {
        InvTakingScreen(
            uiInvTakingSt = InvTakingUIStateModel(
                coilNo = "",
                yyrrcct = "",
                supplier = "",
            ),
            onAction = {}
        )
    }