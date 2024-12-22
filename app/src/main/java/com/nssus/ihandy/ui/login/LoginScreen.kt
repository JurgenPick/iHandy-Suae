package com.nssus.ihandy.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.R
import com.nssus.ihandy.data.constant.AppConstant.NETWORK_IP
import com.nssus.ihandy.model.login.LoginAction
import com.nssus.ihandy.model.login.LoginUIStateModel
import com.nssus.ihandy.model.ui.TextFieldType
import com.nssus.ihandy.ui.login.basecomposable.LoginAppIcon
import com.nssus.ihandy.ui.login.basecomposable.LoginButton
import com.nssus.ihandy.ui.login.basecomposable.LoginTextField
import com.nssus.ihandy.ui.login.constant.LoginConstant.MAX_LENGTH_PASSWORD
import com.nssus.ihandy.ui.theme.White87
import com.nssus.ihandy.ui.theme.Dimens
import com.nssus.ihandy.ui.theme.FontStyles
import com.nssus.ihandy.ui.theme.MainSky

@Composable
fun LoginScreen(
    uiLoginSt : LoginUIStateModel,
    onAction: (LoginAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val userNameFocusRequest = FocusRequester()
    val passwordFocusRequest = FocusRequester()

    var usernameTxt by remember { mutableStateOf(TextFieldValue()) } // TextFieldValue("")
    var passwordTxt by remember { mutableStateOf(TextFieldValue()) } // TextFieldValue("")

    LaunchedEffect(
        uiLoginSt.isNextUserNameAction,
        uiLoginSt.isNextPasswordAction,
        uiLoginSt.isClearTextField
    ){
        when{
            uiLoginSt.isNextUserNameAction -> {
                passwordFocusRequest.requestFocus()
                onAction(LoginAction.SetInitFlagUsername)
            }
            uiLoginSt.isNextPasswordAction -> {
                onAction(LoginAction.SetInitFlagPassword)
                onAction(LoginAction.ClickLoginButton(usernameTxt.text,passwordTxt.text))
            }
            uiLoginSt.isClearTextField -> {
                usernameTxt = TextFieldValue()
                passwordTxt = TextFieldValue()
                userNameFocusRequest.requestFocus()
                onAction(LoginAction.SetInitClearTextFiled)
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainSky)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                focusManager.clearFocus()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            ConstraintLayout {
                val (icon) = createRefs()

                Column(
                    modifier = Modifier
                        .padding(top = 110.dp, bottom = 10.dp)
                        .padding(horizontal = Dimens.padding_bg_main)
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = White87,
                                shape = RoundedCornerShape(Dimens.size_card_corner_large)
                            )
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = Dimens.padding_card_to_content)
                                .padding(top = 80.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            ) {
                                LoginTextField(
                                    titleId = R.string.login_user_id_title,
                                    tfValue = usernameTxt,
                                    tfModifier = Modifier.focusRequester(userNameFocusRequest),
                                    tfType = TextFieldType.Number,
                                    onTfValueChanged = {
                                        usernameTxt = it
                                        onAction(LoginAction.TypingUsernameTextField(usernameTxt.text))
                                    }, tfNextAction = {
                                        onAction(LoginAction.ClickNextUserName)
                                    }
                                )
                                Spacer(modifier = Modifier.height(Dimens.space_textfield_to_textfield))
                                LoginTextField(
                                    titleId = R.string.login_password_title,
                                    tfValue = passwordTxt,
                                    tfMaxLength = MAX_LENGTH_PASSWORD,
                                    tfModifier = Modifier.focusRequester(passwordFocusRequest),
                                    tfType = TextFieldType.Password,
                                    onTfValueChanged = {
                                        passwordTxt = it
                                        onAction(LoginAction.TypingPasswordTextField(passwordTxt.text))
                                    }, tfDoneAction = {
                                        println("Text password")
                                        onAction(LoginAction.ClickNextPassword)
                                    }
                                )
                                Spacer(modifier = Modifier.height(70.dp))
                                LoginButton {
                                    onAction(LoginAction.ClickLoginButton(usernameTxt.text,passwordTxt.text))
                                }
                            }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 14.dp),
                                text = "V.${BuildConfig.VERSION_NAME}",
                                style = FontStyles.txt14,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
                LoginAppIcon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(icon) {
                            top.linkTo(parent.top, 26.dp)
                        }
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = NETWORK_IP ?: "-",
            style = FontStyles.txt14,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, locale = "th")
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiLoginSt =LoginUIStateModel() ,
        onAction = {}
    )
}