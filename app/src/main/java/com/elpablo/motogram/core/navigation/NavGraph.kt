package com.elpablo.motogram.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elpablo.motogram.ui.auth.AuthScreen
import com.elpablo.motogram.ui.auth.AuthViewModel

@Composable
fun SetupNavGraph(startDestination: String) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val modifier = Modifier
    Scaffold(modifier = modifier) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(route = Screen.AUTH.route) {
                val viewModel = hiltViewModel<AuthViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                AuthScreen(
                    modifier = modifier,
                    snackbar = snackbarHostState,
                    uiState = state,
                    uiEvent = viewModel::uiEvent,
                    onNavigate = {  }
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object AUTH : Screen(route = "auth")
    data object DASHBOARD: Screen(route = "dashboard")
}