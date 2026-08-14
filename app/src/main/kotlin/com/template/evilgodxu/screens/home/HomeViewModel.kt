package com.template.evilgodxu.screens.home

import androidx.lifecycle.ViewModel
import com.template.evilgodxu.screens.home.expanded.sidebar.SidebarTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 首页状态管理：设置弹窗与 Tab 切换
class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    fun showSettings() {
        _state.value = _state.value.copy(showSettings = true)
    }

    fun hideSettings() {
        _state.value = _state.value.copy(showSettings = false)
    }

    fun selectTab(tab: SidebarTab) {
        _state.value = _state.value.copy(selectedTab = tab)
    }
}
