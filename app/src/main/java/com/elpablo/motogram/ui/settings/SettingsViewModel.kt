package com.elpablo.motogram.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.motogram.domain.model.User
import com.vk.id.VKID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsViewState())
    val uiState: StateFlow<SettingsViewState> get() = _uiState

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }
            _uiState.update { state ->
                state.copy(
                    user = User(
                        id = VKID.instance.accessToken?.userID,
                        firstName = VKID.instance.accessToken?.userData?.firstName,
                        lastName = VKID.instance.accessToken?.userData?.lastName,
                        photoURL = VKID.instance.accessToken?.userData?.photo200
                    ),
                    isLoading = false
                )
            }
        }
    }

    fun uiEvent(event: SettingsEvent) {

    }
}