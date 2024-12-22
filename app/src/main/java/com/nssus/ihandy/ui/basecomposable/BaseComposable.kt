package com.nssus.ihandy.ui.basecomposable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.nssus.ihandy.R
import com.nssus.ihandy.model.ui.TextFieldAlign
import com.nssus.ihandy.model.ui.TextFieldColor
import com.nssus.ihandy.model.ui.TextFieldType
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainSky
import com.nssus.ihandy.ui.theme.SilverGray

@Composable
fun CustomLoading() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
        CircularProgressIndicator(color = MainSky)
    }
}

@Composable
fun BackImageIcon(onBackIconClick: () -> Unit) {
    Image(
        modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .clickable { onBackIconClick() },
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = "Back Icon"
    )
}

@Composable
fun BaseHeader(
    @StringRes headerId: Int,
    style: TextStyle = FontStyles.txt28
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = headerId),
        style = style,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TopTitleAndBaseTextField(
    modifier: Modifier = Modifier,
    // Title
    @StringRes titleId: Int,
    titleStyle: TextStyle = FontStyles.txt24,
    titleFontWeight: FontWeight = FontWeight.Medium,
    // TextField
    tfModifier: Modifier = Modifier.fillMaxWidth(),
    tfType: TextFieldType = TextFieldType.Text,
    tfAlign: TextFieldAlign = TextFieldAlign.Start,
    tfValue: TextFieldValue,
    onTfValueChanged: (TextFieldValue) -> Unit,
    tfPlaceholder: String? = null,
    tfIsError: Boolean = false, //
    tfErrorMessage: String? = null,
    tfSupportMessage: String? = null,
    tfIsEnabled: Boolean = true,
    tfStyle: TextStyle = FontStyles.txt20,
    tfColor: TextFieldColor = TextFieldColor(),
//    tfBorder: Dp = Dimens.border_text_field,
    tfMaxLine: Int = 1,
    tfMaxLength: Int = 250,
    tfIsSingleLine: Boolean = true,
    tfIsAutoGenUpperCase: Boolean = true,
    tfIsAutoReplacePrefix: Boolean = false,
    tfCheckedPrefixText: String = "-",
    tfNewPrefixText: String = "",
    onTfNextActionClick: () -> Unit,
    onTfDoneActionClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = titleId),
            style = titleStyle,
            fontWeight = titleFontWeight
        )
        BaseTextField(
            modifier = tfModifier,
            textFieldType = tfType,
            align = tfAlign,
            value = tfValue,
            onValueChanged = {
                if (it.text.length <= tfMaxLength) onTfValueChanged(it) //
            },
            placeholder = tfPlaceholder,
            isTextFieldError = tfIsError, //
            errorMessage = tfErrorMessage,
            supportMessage = tfSupportMessage,
            enabled = tfIsEnabled,
            style = tfStyle,
            color = tfColor,
//            border = tfBorder,
            maxLine = tfMaxLine,
            singleLine = tfIsSingleLine,
            isAutoGenUpperCase = tfIsAutoGenUpperCase,
            isAutoReplacePrefix = tfIsAutoReplacePrefix,
            checkedPrefixText = tfCheckedPrefixText,
            newPrefixText = tfNewPrefixText,
            onNextActionClick = { onTfNextActionClick() },
            onDoneActionClick = { onTfDoneActionClick() }
        )
    }
}

@Composable
fun PrefixTitleAndBaseTextField(
    modifier: Modifier = Modifier,
    // Title
    @StringRes titleId: Int,
    titleStyle: TextStyle = FontStyles.txt24,
    titleFontWeight: FontWeight = FontWeight.Medium,
    // TextField
    tfModifier: Modifier = Modifier.fillMaxWidth(),
    tfType: TextFieldType = TextFieldType.Text,
    tfAlign: TextFieldAlign = TextFieldAlign.Start,
    tfValue: TextFieldValue,
    onTfValueChanged: (TextFieldValue) -> Unit,
    tfPlaceholder: String? = null,
    tfIsError: Boolean = false, //
    tfErrorMessage: String? = null,
    tfSupportMessage: String? = null,
    tfIsEnabled: Boolean = true,
    tfStyle: TextStyle = FontStyles.txt20,
    tfColor: TextFieldColor = TextFieldColor(),
//    tfBorder: Dp = Dimens.border_text_field,
    tfMaxLine: Int = 1,
    tfMaxLength: Int = 250,
    tfIsSingleLine: Boolean = true,
    tfIsAutoGenUpperCase: Boolean = true,
    tfIsAutoReplacePrefix: Boolean = false,
    tfCheckedPrefixText: String = "-",
    tfNewPrefixText: String = "",
    onTfNextActionClick: () -> Unit,
    onTfDoneActionClick: () -> Unit ={},
) {
    Row(modifier = modifier) {
        Text(
            text = stringResource(id = titleId),
            style = titleStyle,
            fontWeight = titleFontWeight
        )
        Spacer(modifier = Modifier.width(Dimens.space_prefix_text_to_textfield))
        BaseTextField(
            modifier = tfModifier,
            textFieldType = tfType,
            align = tfAlign,
            value = tfValue,
            onValueChanged = {
                if (it.text.length <= tfMaxLength) onTfValueChanged(it) //
            },
            placeholder = tfPlaceholder,
            isTextFieldError = tfIsError, //
            errorMessage = tfErrorMessage,
            supportMessage = tfSupportMessage,
            enabled = tfIsEnabled,
            style = tfStyle,
            color = tfColor,
//            border = tfBorder,
            maxLine = tfMaxLine,
            singleLine = tfIsSingleLine,
            isAutoGenUpperCase = tfIsAutoGenUpperCase,
            isAutoReplacePrefix = tfIsAutoReplacePrefix,
            checkedPrefixText = tfCheckedPrefixText,
            newPrefixText = tfNewPrefixText,
            onNextActionClick = { onTfNextActionClick() },
            onDoneActionClick = { onTfDoneActionClick() }
        )
    }
}

@Composable
fun RowPairButton(
    @StringRes textLeftButton: Int = R.string.common_send_button,
    @StringRes textRightButton: Int = R.string.common_clear_button,
    betweenSpace: Dp = Dimens.space_between_button_to_button,
    bottomSpace: Dp = Dimens.space_bottom_content_card_to_button,
    leftModifier: Modifier = Modifier,
    rightModifier: Modifier = Modifier,
    contentPaddingLeft: PaddingValues = PaddingValues(0.dp),
    contentPaddingRight: PaddingValues = PaddingValues(0.dp),
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    Column {
        Row {
            BaseButton(
                modifier = leftModifier.weight(1f),
                text = stringResource(id = textLeftButton),
                contentPadding = contentPaddingLeft,
                onButtonClick = { onLeftButtonClick() }
            )
            Spacer(modifier = Modifier.width(betweenSpace))
            BaseButton(
                modifier = rightModifier.weight(1f),
                text = stringResource(id = textRightButton),
                contentPadding = contentPaddingRight,
                onButtonClick = { onRightButtonClick() }
            )
        }
        Spacer(modifier = Modifier.height(bottomSpace))
    }
}

@Composable
fun TitleAndGrayBgValueText(
    modifier: Modifier = Modifier,
    // Title
    @StringRes titleId: Int,
    titleTextStyle: TextStyle = FontStyles.txt24,
    titleFontWeight: FontWeight = FontWeight.Medium,
    titleTextAlign: TextAlign = TextAlign.Center,
    // Value
    value: String,
    valueTextStyle: TextStyle = FontStyles.txt20,
    valueTextAlign: TextAlign = TextAlign.Center,
    valueVerticalPadding: Dp = Dimens.padding_vertical_gray_bg_value
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = titleId),
            style = titleTextStyle,
            fontWeight = titleFontWeight,
            textAlign = titleTextAlign
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(SilverGray)
                .padding(vertical = valueVerticalPadding),
            text = value,
            style = valueTextStyle,
            textAlign = valueTextAlign
        )
    }
}

@Composable
fun PrefixTitleAndGrayBgValueTextWithIcon(
    modifier: Modifier = Modifier,
    // Title
    @StringRes titleId: Int = R.string.common_result_title,
    titleTextStyle: TextStyle = FontStyles.txt24,
    titleFontWeight: FontWeight = FontWeight.Medium,
    // Value
    value: String, // @StringRes valueId: Int
    valueTextStyle: TextStyle = FontStyles.txt20,
    valueTextAlign: TextAlign = TextAlign.Center,
    // Icon
    @DrawableRes iconId: Int? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleId),
            style = titleTextStyle,
            fontWeight = titleFontWeight
        )
        Spacer(modifier = Modifier.width(Dimens.space_prefix_text_to_textfield))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(SilverGray)
                .padding(vertical = Dimens.padding_vertical_gray_bg_value)
                .weight(1f),
            text = value, // stringResource(id = valueId),
            style = valueTextStyle,
            textAlign = valueTextAlign
        )
        iconId?.let {
            Spacer(modifier = Modifier.width(Dimens.space_textfield_to_icon))
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = it),
                contentDescription = "Icon"
            )
        }
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun BaseComposableComponentPreview() {
    PrefixTitleAndGrayBgValueTextWithIcon(
        value = stringResource(id = R.string.common_ok_text),
        iconId = R.drawable.ic_dialog_green_tick
//        iconId = null
    )
//    PrefixTitleAndBaseTextField(
//        titleId = R.string.login_user_id_title,
//        tfValue = TextFieldValue(),
//        onTfValueChanged = {}
//    )
}