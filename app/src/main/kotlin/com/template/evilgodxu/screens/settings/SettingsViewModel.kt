package com.template.evilgodxu.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 页面级 ViewModel：主题与语言由单一数据源（DataStore）驱动
class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val appVersion: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(version = appVersion))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        // 主题与语言均由 DataStore 流驱动，写入后回流更新 UI
        viewModelScope.launch {
            settingsRepository.settings.collect { settings ->
                _uiState.update { it.copy(themeMode = settings.themeMode) }
            }
        }
        viewModelScope.launch {
            settingsRepository.appLanguage.collect { language ->
                _uiState.update { it.copy(language = language) }
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.saveThemeMode(mode)
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch {
            settingsRepository.setAppLanguage(language)
        }
    }
}