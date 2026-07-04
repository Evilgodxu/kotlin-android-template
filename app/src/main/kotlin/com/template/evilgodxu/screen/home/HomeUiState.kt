package com.template.evilgodxu.screen.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val themeMode: String = "system",
    val language: String = "system",
    val showSettings: Boolean = false,
)
