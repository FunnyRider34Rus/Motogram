package com.elpablo.motogram.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.elpablo.motogram.core.navigation.Screen
import com.vk.id.VKID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {
    private val _startDestination: MutableState<String> = mutableStateOf(Screen.AUTH.route)
    val startDestination: State<String> get() = _startDestination

    init {
        if (VKID.instance.accessToken?.userID == null) {
            _startDestination.value = Screen.AUTH.route

        }
        else {
            _startDestination.value = Screen.DASHBOARD.route
        }
    }
}