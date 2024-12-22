package com.nssus.ihandy.ui.common.relabel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.nssus.ihandy.R
import com.nssus.ihandy.data.extension.getSelectedItem
import com.nssus.ihandy.model.common.relabel.ReLabelAction
import com.nssus.ihandy.model.common.relabel.ReLabelUIStateModel
import com.nssus.ihandy.model.shipping.chargerelo.ChargeReloUIStateModel
import com.nssus.ihandy.model.ui.DropdownUIModel
import com.nssus.ihandy.ui.basecomposable.BaseHeader
import com.nssus.ihandy.ui.basecomposable.BaseDropdownDialog
import com.nssus.ihandy.ui.basecomposable.BaseContentCardWithBackButton
import com.nssus.ihandy.ui.basecomposable.RowPairButton
import com.nssus.ihandy.ui.basecomposable.TitleAndGrayBgValueText
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.common.relabel.constant.ReLabelConstant.MAX_LENGTH_LABEL_NO_1
import com.nssus.ihandy.ui.common.relabel.constant.ReLabelConstant.MAX_LENGTH_LABEL_NO_2
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@Composable
fun ReLabelScreen(
    dataLs: List<DropdownUIModel>,
//    relabelNo1: String,
//    relabelNo2: String,
    uiReLabelSt: ReLabelUIStateModel,
    onAction: (ReLabelAction) -> Unit
) {
    var relabelNo1Txt by remember { mutableStateOf(TextFieldValue(uiReLabelSt.relabelNo1)) }
    var relabelNo2Txt by remember { mutableStateOf(TextFieldValue(uiReLabelSt.relabelNo2)) }

    BaseContentCardWithBackButton(onBackIconClick = { onAction(ReLabelAction.GoBack) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
            BaseHeader(headerId = R.string.relabel_menu_title)
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(Dimens.space_top_content_card_to_header))
                    BaseDropdownDialog(
                        dataList = dataLs,
                        onDropdownItemSelected = { onAction(ReLabelAction.SelectDataDropdown(it)) },
                        selectedItem = dataLs.getSelectedItem() // dataLs.find { it.isSelected }
                    )
                    TopTitleAndBaseTextField(modifier = Modifier.weight(.53f),
                        titleId = R.string.relabel_no_title_1,
                        tfValue = relabelNo1Txt,
                        tfMaxLength = MAX_LENGTH_LABEL_NO_1, //
                        onTfValueChanged = {
                            relabelNo1Txt = it
                            onAction(ReLabelAction.TypingReLabel1TextField(it.text))
                        },
                        onTfNextActionClick = {}
                    )
                    Spacer(modifier = Modifier.width(Dimens.space_textfield_to_textfield))
                    TopTitleAndBaseTextField(titleId = R.string.relabel_no_title_2,
                        tfValue = relabelNo2Txt,
                        tfMaxLength = MAX_LENGTH_LABEL_NO_2, //
                        onTfValueChanged = {
                            relabelNo2Txt = it
                            onAction(ReLabelAction.TypingReLabel2TextField(it.text))
                        },
                        onTfNextActionClick = {}
                    )
                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.padding_inner_content_to_top_title_gray_value_2
                        ),
                        titleId = R.string.relabel_display_status_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "${uiReLabelSt.relabelNo1}", //
                    )

                    TitleAndGrayBgValueText(
                        modifier = Modifier.padding(
                            top = Dimens.space_textfield_to_textfield,
                            bottom = Dimens.padding_below_bottom_title_gray_value
                        ),
                        titleId = R.string.relabel_display_yyrrcct_title,
                        titleTextStyle = FontStyles.txt28,
                        value = "${uiReLabelSt.relabelNo2}", //
                    )
                }
            }
        }

        RowPairButton(onLeftButtonClick = { onAction(ReLabelAction.ClickSendButton) },
            onRightButtonClick = {
                relabelNo1Txt = TextFieldValue()
                relabelNo2Txt = TextFieldValue()

                onAction(ReLabelAction.ClickClearButton)
            })
    }
}


@Preview(showBackground = true, locale = "th")
@Composable
fun ReLabelScreenPreview() {
    ReLabelScreen(
        dataLs = listOf(),
        uiReLabelSt = ReLabelUIStateModel(
            relabelNo1 = "",
            relabelNo2 = "",
        ),
        onAction = {}
    )
}