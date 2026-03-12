package com.elpablo.motogram.ui.auth

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elpablo.motogram.core.components.MotogramLoader
import com.elpablo.motogram.core.components.MotogramTopbar
import com.elpablo.motogram.ui.MainActivity
import com.vk.id.onetap.common.OneTapStyle
import com.vk.id.onetap.common.button.style.OneTapButtonCornersStyle
import com.vk.id.onetap.common.button.style.OneTapButtonSizeStyle
import com.vk.id.onetap.compose.onetap.OneTap

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
    uiState: AuthViewState,
    uiEvent: (AuthEvent) -> Unit,
    onNavigate: () -> Unit
) {

    val activity = LocalActivity.current
    val context = LocalContext.current

    if (uiState.isLoggedIn) {
        onNavigate.invoke()
    }
    if (uiState.isLoading) {
        MotogramLoader()
    }
    if (uiState.isError) {
        LaunchedEffect(null) {
            uiState.error.let { msg -> snackbar.showSnackbar(msg) }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MotogramTopbar("Авторизация")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        OneTap(
            modifier = Modifier
                .fillMaxWidth()
                .padding(64.dp),
            onAuth = { oAuth, token ->
                uiEvent(AuthEvent.AuthSuccess)
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            },
            onFail = { oAuth, error ->
                uiEvent(AuthEvent.AuthFail(error.description))
            },
            signInAnotherAccountButtonEnabled = true,
            style = OneTapStyle.Light(
                cornersStyle = OneTapButtonCornersStyle.Custom(16f),
                sizeStyle = OneTapButtonSizeStyle.DEFAULT
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        snackbar = SnackbarHostState(),
        uiState = AuthViewState(),
        uiEvent = { },
        onNavigate = { }
    )
}