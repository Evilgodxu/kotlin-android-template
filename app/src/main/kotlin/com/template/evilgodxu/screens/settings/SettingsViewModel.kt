package com.template.evilgodxu.screens.settings

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val settingsRepository: SettingsRepository,
) : AndroidViewModel(application) {

    private val context get() = getApplication<Application>()

    private val _uiState = MutableStateFlow(
        SettingsUiState(version = getVersion()),
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        // 主题由 DataStore 流驱动；语言切换后由同一个数据源回流更新 UI
        viewModelScope.launch {
            settingsRepository.settings.collect { settings ->
                _uiState.update { it.copy(themeMode = settings.themeMode) }
            }
        }
        viewModelScope.launch {
            _uiState.update { it.copy(language = settingsRepository.getAppLanguage()) }
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

    private fun getVersion(): String {
        // PackageInfoFlags 重载自 API 33 引入，低版本回退旧重载：
        // https://developer.android.com/reference/android/content/pm/PackageManager#getPackageInfo(java.lang.String,%20android.content.pm.PackageManager.PackageInfoFlags)
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0L))
        } else {
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
        return packageInfo.versionName.orEmpty()
    }
}