package com.nssus.ihandy.ui.basecomposable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.ui.theme.BaseGray
import com.nssus.ihandy.ui.theme.Black50
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.Sky28

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    textButtonModifier: Modifier = Modifier.padding(4.dp),
    text: String,
    textStyle: TextStyle = FontStyles.txt20.copy(fontWeight = FontWeight.SemiBold),
    textColor: Color = Color.Black,
    borderColor: Color = Black50,
    buttonColor: Color = Sky28,
    shape: Shape = RoundedCornerShape(corner = CornerSize(Dimens.size_button_corner_small)),
    enabled: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        border = BorderStroke(Dimens.border_button, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor =  buttonColor,
            contentColor = borderColor,
            disabledContainerColor = BaseGray,
            disabledContentColor = borderColor
        ),
        enabled = enabled,
        shape = shape,
        elevation = elevation,
        contentPadding = contentPadding,
        onClick = { onButtonClick() }
    )
    {
        Text(
            modifier = textButtonModifier,
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun BaseDialogButton(
    modifier: Modifier = Modifier,
    text: String,
    borderColor: Color = BaseGray,
    buttonColor: Color = BaseGray,
    textColor: Color = Color.Black,
    onButtonClick: () -> Unit
) {
    BaseButton(
        modifier = modifier,
        textButtonModifier = Modifier.padding(0.dp),
        text = text,
        textStyle = FontStyles.txt14,
        borderColor = borderColor,
        buttonColor = buttonColor,
        textColor = textColor,
        shape = RoundedCornerShape(corner = CornerSize(5.dp)), //
        onButtonClick = { onButtonClick() }
    )
}