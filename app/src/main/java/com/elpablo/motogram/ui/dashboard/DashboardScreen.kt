package com.elpablo.motogram.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.elpablo.motogram.R
import com.elpablo.motogram.core.components.MotogramTopbar

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        MotogramTopbar(title = stringResource(R.string.screen_dashboard_title))
    }
}