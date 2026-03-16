package com.elpablo.motogram.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elpablo.motogram.ui.auth.AuthScreen
import com.elpablo.motogram.ui.auth.AuthViewModel
import com.elpablo.motogram.ui.chats.ChatsScreen
import com.elpablo.motogram.ui.dashboard.DashboardScreen
import com.elpablo.motogram.ui.ride.RideScreen
import com.elpablo.motogram.ui.settings.SettingsScreen
import com.elpablo.motogram.ui.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(startDestination: String) {
    val navController = rememberNavController()
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { MotogramBottomBar(navController = navController, scrollBehavior = scrollBehavior) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
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
                    onNavigate = { navController.navigate(Screen.DASHBOARD.route) }
                )
            }
            composable(route = Screen.DASHBOARD.route) {
                DashboardScreen(modifier = modifier)
            }
            composable(route = Screen.RIDE.route) {
                RideScreen(modifier = modifier)
            }
            composable(route = Screen.CHATS.route) {
                ChatsScreen(modifier = modifier)
            }
            composable(route = Screen.SETTINGS.route) {
                val viewModel = hiltViewModel<SettingsViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                SettingsScreen(
                    modifier = modifier,
                    snackbar = snackbarHostState,
                    uiState = state,
                    uiEvent = viewModel::uiEvent
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object AUTH : Screen(route = "auth")
    data object DASHBOARD: Screen(route = "dashboard")
    data object RIDE: Screen(route = "ride")
    data object CHATS: Screen(route = "chats")
    data object SETTINGS: Screen(route = "settings")
}