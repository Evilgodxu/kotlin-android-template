package com.template.evilgodxu.screens.home

import com.template.evilgodxu.screens.home.expanded.sidebar.SidebarTab

data class HomeUiState(
    val isLoading: Boolean = false,
    val themeMode: String = "system",
    val language: String = "system",
    val showSettings: Boolean = false,
    val selectedTab: SidebarTab = SidebarTab.HOME,
)
