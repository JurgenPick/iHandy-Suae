package com.nssus.ihandy.ui.login.basecomposable

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nssus.ihandy.R
import com.nssus.ihandy.model.ui.TextFieldType
import com.nssus.ihandy.ui.basecomposable.BaseButton
import com.nssus.ihandy.ui.basecomposable.TopTitleAndBaseTextField
import com.nssus.ihandy.ui.theme.ButtonSky
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainSky

@Composable
fun LoginAppIcon(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(140.dp),
                painter = painterResource(id = R.drawable.ic_nssus_app),
                contentDescription = "App Icon"
            )
        }
    }
}

@Composable
fun LoginTextField(
    @StringRes titleId: Int,
    tfType: TextFieldType = TextFieldType.Text,
    tfModifier: Modifier = Modifier,
    tfValue: TextFieldValue,
    tfMaxLength: Int = 250,
    tfNextAction: () -> Unit = {},
    tfDoneAction: () -> Unit = {},
    onTfValueChanged: (TextFieldValue) -> Unit
) {
    TopTitleAndBaseTextField(
        modifier = tfModifier,
        titleId = titleId,
        titleStyle = FontStyles.txt14,
        titleFontWeight = FontWeight.Normal,
        tfType = tfType,
        tfValue = tfValue,
        tfMaxLength = tfMaxLength,
        tfIsAutoGenUpperCase = false,
        onTfValueChanged = {
            onTfValueChanged(it)
        },
        onTfNextActionClick = { tfNextAction() },
        onTfDoneActionClick = { tfDoneAction() }
    )
}

@Composable
fun LoginButton(onLoginButtonClick: () -> Unit) {
    BaseButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        textButtonModifier = Modifier,
        text = stringResource(id = R.string.login_login_button),
        textColor = Color.White,
        textStyle = FontStyles.txt20,
        buttonColor = ButtonSky,
        borderColor = MainSky,
        shape = RoundedCornerShape(corner = CornerSize(Dimens.size_button_corner_medium)),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 3.dp),
        onButtonClick = { onLoginButtonClick() }
    )
}