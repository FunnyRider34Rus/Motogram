package com.elpablo.motogram.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.elpablo.motogram.core.navigation.SetupNavGraph
import com.elpablo.motogram.core.theme.MotogramTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotogramTheme {
                MotogramApp(startDestination = viewModel.startDestination.value)
            }
        }
    }
}

@Composable
fun MotogramApp(startDestination: String) {
    SetupNavGraph(
        startDestination = startDestination,
    )
}