package com.template.evilgodxu.screens.home

import com.template.evilgodxu.screens.home.expanded.sidebar.SidebarTab

// 首页 UI 状态
data class HomeUiState(
    val isLoading: Boolean = false,
    val selectedTab: SidebarTab = SidebarTab.HOME,
)
