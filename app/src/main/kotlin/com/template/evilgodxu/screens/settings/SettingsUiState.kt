package com.template.evilgodxu.screens.settings

import com.template.evilgodxu.data.settings.ThemeMode

// 设置页 UI 状态
data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)
