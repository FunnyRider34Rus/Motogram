package com.elpablo.motogram.ui.settings

import com.elpablo.motogram.domain.model.User

data class SettingsViewState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)
