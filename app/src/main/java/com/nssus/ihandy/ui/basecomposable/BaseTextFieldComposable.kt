package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.data.constant.ValueConstant.PATTERN_NUMBER
import com.nssus.ihandy.data.extension.replaceStartSpecificTextToNewValue
import com.nssus.ihandy.model.ui.TextFieldAlign
import com.nssus.ihandy.model.ui.TextFieldColor
import com.nssus.ihandy.model.ui.TextFieldType
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType = TextFieldType.Text,
    align: TextFieldAlign = TextFieldAlign.Start,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    placeholder: String? = null,
    isTextFieldError: Boolean = false, //
    errorMessage: String? = null,
    supportMessage: String? = null,
    enabled: Boolean = true,
    style: TextStyle = FontStyles.txt20,
    color: TextFieldColor = TextFieldColor(),
//    border: Dp = Dimens.border_text_field,
    maxLine: Int = 1,
    singleLine: Boolean = true,
    isAutoGenUpperCase: Boolean = true,
    isAutoReplacePrefix: Boolean = false,
    checkedPrefixText: String = "-",
    newPrefixText: String = "",
    onNextActionClick: () -> Unit,
    onDoneActionClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val numberPattern = remember { Regex(PATTERN_NUMBER) }

    val bgColor = if (enabled) color.enableColor else color.disableColor
    val borderColor = if (isTextFieldError) color.errorBorderColor else color.borderColor // color.borderColor

    val keyboardOptions = when (textFieldType) {
        TextFieldType.Number -> KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next)
        TextFieldType.Password -> KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        else -> KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
    }

    val visualTransformation = when(textFieldType) {
        TextFieldType.Password -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            when (textFieldType) {
                TextFieldType.Number -> if (it.text.isEmpty() || it.text.matches(numberPattern)) onValueChanged(it)
                else -> {
                    // Get the updated result text
                    var updatedTxt = it

                    // If TRUE, replace from specific prefix text to new value
                    if (isAutoReplacePrefix)
                        updatedTxt = updatedTxt.copy(
                            text = updatedTxt.text.replaceStartSpecificTextToNewValue(checkedPrefixText, newPrefixText)
                        )

                    // If TRUE, convert the updated result text to uppercase
                    if (isAutoGenUpperCase)
                        updatedTxt = updatedTxt.copy(text = updatedTxt.text.uppercase())

                    // Return the updated result text
                    onValueChanged(updatedTxt)
                }
            }
        },
        visualTransformation = visualTransformation,
        enabled = enabled,
        maxLines = maxLine,
        singleLine = singleLine,
        textStyle = if (align == TextFieldAlign.End) style.copy(textAlign = TextAlign.End, color = color.textColor)
        else style.copy(textAlign = TextAlign.Start, color = color.textColor),
        decorationBox = { innerTextField ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(corner = CornerSize(Dimens.size_textfield_corner_small)))
                        .background(bgColor)
                        .border(
                            if (isTextFieldError) 2.dp else Dimens.border_text_field, // convert to Dimens
                            borderColor,
                            RoundedCornerShape(corner = CornerSize(Dimens.size_textfield_corner_small))
                        )
                        .heightIn(min = Dimens.min_height_text_field),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(Dimens.padding_normal))
                    Column(modifier = Modifier.weight(1f),) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            placeholder?.let {
                                if (value.text.isEmpty()) {
                                    Text(
                                        modifier = Modifier.align(if (align == TextFieldAlign.End) Alignment.CenterEnd else Alignment.CenterStart),
                                        text = it,
                                        style = style.copy(
                                            color = color.placeHolderColor,
                                            textAlign = if (align == TextFieldAlign.End) TextAlign.End else TextAlign.Start
                                        ),
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.align(if (align == TextFieldAlign.End) Alignment.CenterEnd else Alignment.CenterStart)
                            ) {
                                innerTextField()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(Dimens.padding_small))
                }

                if (!errorMessage.isNullOrEmpty() && isTextFieldError) { // !errorMessage.isNullOrEmpty()
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        style = FontStyles.txt20.copy(color.errorMsgColor),
                        textAlign = if (align == TextFieldAlign.End) TextAlign.End else TextAlign.Start
                    )
                } else if (!supportMessage.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = supportMessage,
                        style = FontStyles.txt20.copy(color.supportMsgColor),
                        textAlign = TextAlign.End
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onNext = {
                println("onNext Click")
                onNextActionClick()
//                focusManager.clearFocus()
            },
            onDone = {
                println("onDone Click")
                onDoneActionClick()
            }
        ),
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun BaseTextFieldWithTrailingIcon(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType = TextFieldType.Text,
    align: TextFieldAlign = TextFieldAlign.Start,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit = {},
    placeholder: String? = null,
    trailingIcon: @Composable () -> Unit,
    style: TextStyle = FontStyles.txt20,
    color: TextFieldColor = TextFieldColor(),
    border: Dp = Dimens.border_text_field,
    maxLine: Int = 1,
    singleLine: Boolean = true,
    enabled: Boolean = true
) {
    val focusManager = LocalFocusManager.current
    val numberPattern = remember { Regex(PATTERN_NUMBER) }

    val keyboardOptions = when (textFieldType) {
        TextFieldType.Number -> KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        TextFieldType.Password -> KeyboardOptions(keyboardType = KeyboardType.Password)
        else -> KeyboardOptions(keyboardType = KeyboardType.Text)
    }

    val visualTransformation = when(textFieldType) {
        TextFieldType.Password -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            when (textFieldType) {
                TextFieldType.Number ->
                    if (it.text.isEmpty() || it.text.matches(numberPattern)) onValueChanged(it)
                else -> onValueChanged(it)
            }
        },
        visualTransformation = visualTransformation,
        maxLines = maxLine,
        singleLine = singleLine,
        enabled = enabled,
        textStyle = if (align == TextFieldAlign.End) style.copy(textAlign = TextAlign.End, color = color.textColor)
        else style.copy(textAlign = TextAlign.Start, color = color.textColor),
        decorationBox = { innerTextField ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(corner = CornerSize(Dimens.size_textfield_corner_small)))
                        .background(color.enableColor)
                        .border(
                            border,
                            color.borderColor,
                            RoundedCornerShape(corner = CornerSize(Dimens.size_textfield_corner_small))
                        )
                        .heightIn(min = Dimens.min_height_text_field),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(Dimens.padding_normal))
                    Column(modifier = Modifier.weight(.9f)) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            placeholder?.let {
                                if (value.text.isEmpty()) {
                                    Text(
                                        modifier = Modifier.align(if (align == TextFieldAlign.End) Alignment.CenterEnd else Alignment.CenterStart),
                                        text = it,
                                        style = style.copy(
                                            color = color.placeHolderColor,
                                            textAlign = if (align == TextFieldAlign.End) TextAlign.End else TextAlign.Start
                                        ),
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.align(
                                    if (align == TextFieldAlign.End) Alignment.CenterEnd else Alignment.CenterStart
                                )
                            ) {
                                innerTextField()
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Column(
                        modifier = Modifier.weight(.1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        trailingIcon.invoke()
                    }
                }
            }
        },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() },),
        keyboardOptions = keyboardOptions
    )
}