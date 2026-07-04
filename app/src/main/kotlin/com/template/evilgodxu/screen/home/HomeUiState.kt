package com.template.evilgodxu.screen.home

import com.template.evilgodxu.screen.home.landscape.left_panel.LandscapeTab

data class HomeUiState(
    val isLoading: Boolean = false,
    val themeMode: String = "system",
    val language: String = "system",
    val showSettings: Boolean = false,
    val selectedTab: LandscapeTab = LandscapeTab.HOME,
)
