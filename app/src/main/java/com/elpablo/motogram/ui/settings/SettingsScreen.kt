package com.elpablo.motogram.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.motogram.R
import com.elpablo.motogram.core.components.MotogramLoader
import com.elpablo.motogram.core.components.MotogramTopbar

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
    uiState: SettingsViewState,
    uiEvent: (SettingsEvent) -> Unit)
{

    if (uiState.isLoading) {
        MotogramLoader()
    }
    if (uiState.isError) {
        LaunchedEffect(null) {
            if (uiState.error.isNotEmpty()) {
                uiState.error.let { msg ->
                    snackbar.showSnackbar(msg)
                }
            } else {
                snackbar.showSnackbar("Unknown error")
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MotogramTopbar(title = stringResource(R.string.screen_settings_title))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.tertiary),
            verticalAlignment = Alignment.CenterVertically,
            ) {
            GlideImage(
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(CircleShape),
                model = uiState.user.photoURL,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth().padding(end = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text(
                    text = uiState.user.firstName + " " + uiState.user.lastName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "@id"+uiState.user.id,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}