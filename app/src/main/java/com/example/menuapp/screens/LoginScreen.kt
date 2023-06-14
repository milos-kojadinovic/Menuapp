package com.example.menuapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.menuapp.R
import com.example.menuapp.saveToken
import com.example.menuapp.test.TestTag
import com.example.menuapp.ui.theme.HintColor
import com.example.menuapp.ui.theme.SubtitleColor
import com.example.menuapp.ui.theme.buttonStyle
import com.example.menuapp.viewmodels.LoginViewModel
import com.example.menuapp.viewmodels.LoginViewModelIntent
import com.example.menuapp.viewmodels.LoginViewModelState
import org.koin.androidx.compose.getViewModel

private const val TAG = "LOGIN_SCREEN"

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = getViewModel(),
    navigateNext: () -> Unit
) {

    val viewModelState by loginViewModel.state.observeAsState(LoginViewModelState.Idle)
    val context = LocalContext.current

    when (val state = viewModelState) {
        LoginViewModelState.Loading -> {
            LoadingScreen()
        }
        //TODO Error states should have its own UI
        is LoginViewModelState.Error, LoginViewModelState.Idle -> {
            if (state is LoginViewModelState.Error) {
                HandleError(state.message)
            }
            LoginScreen(viewModelState = state) { email, password ->
                loginViewModel.handleIntent(
                    LoginViewModelIntent.TryLogin(
                        email = email,
                        password = password
                    )
                )
            }
        }

        is LoginViewModelState.UserLoggedIn -> {
            LaunchedEffect(key1 = true) {
                context.saveToken(state.token)
                navigateNext()
            }
        }
    }


}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginScreen(
    viewModelState: LoginViewModelState?,
    handleIntent: (String, String) -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Image(
                modifier = Modifier.padding(bottom = 86.dp),
                painter = painterResource(id = R.drawable.login_icon),
                contentDescription = stringResource(
                    id = R.string.easiest_way_to_order
                )
            )

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.easiest_way_to_order),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(bottom = 64.dp),
                text = stringResource(R.string.no_lines_no_waiting),
                color = SubtitleColor,
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.testTag(TestTag.EMAIL),
                onChanged = { email = it },
                hint = stringResource(R.string.email)
            )

            TextField(
                modifier = Modifier.testTag(TestTag.PASSWORD),
                onChanged = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                hint = stringResource(R.string.password)
            )

            Button(
                modifier = Modifier
                    .testTag(TestTag.LOGIN_BUTTON)
                    .padding(12.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                onClick = {
                    if (viewModelState == LoginViewModelState.Idle) {
                        handleIntent(email, password)
                    } else {
                        Log.d(TAG, "Can not request login in current state")
                    }
                }
            ) {
                Text(style = buttonStyle, text = stringResource(R.string.sign_in))
            }
        }

    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextField(
    modifier: Modifier = Modifier,
    onChanged: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    hint: String
) {
    var password by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 12.dp),
        value = password,
        visualTransformation = visualTransformation,
        onValueChange = {
            password = it
            onChanged(it.text)
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedLabelColor = HintColor,
            unfocusedIndicatorColor = HintColor,
            containerColor = Color.White
        ),
        label = { Text(text = hint) },
    )
}