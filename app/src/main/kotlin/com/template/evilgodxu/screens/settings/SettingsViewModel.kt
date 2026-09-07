package com.template.evilgodxu.screens.settings

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.utils.localization.LocalizationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel(
    application: Application,
    private val settingsRepository: SettingsRepository,
) : AndroidViewModel(application), KoinComponent {

    private val context get() = getApplication<Application>()
    private val localizationManager: LocalizationManager by inject()

    private val _uiState = MutableStateFlow(
        SettingsUiState(version = getVersion()),
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
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
        _uiState.update { it.copy(language = language) }
        localizationManager.applyAppLocale(localizationManager.resolveLanguage(language))
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