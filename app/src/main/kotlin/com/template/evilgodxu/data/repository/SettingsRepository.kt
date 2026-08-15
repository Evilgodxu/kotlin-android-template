package com.template.evilgodxu.data.repository

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.datastore.preferences.core.edit
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.SettingsKeys
import com.template.evilgodxu.data.settings.SettingsState
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.data.settings.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// 设置仓库：数据层入口，封装 DataStore 数据源
class SettingsRepository(private val context: Context) {

    // 设置状态流：主题模式驱动全局配色
    val settings: Flow<SettingsState> = context.settingsDataStore.data.map { preferences ->
        SettingsState(
            themeMode = ThemeMode.fromValue(preferences[SettingsKeys.THEME_MODE] ?: ThemeMode.SYSTEM.value),
        )
    }

    // 应用语言流：Android 12L 及以下读 DataStore，更高版本交系统
    val appLanguage: Flow<AppLanguage> = context.settingsDataStore.data.map { preferences ->
        AppLanguage.entries.find { it.languageTag == preferences[SettingsKeys.LANGUAGE] } ?: AppLanguage.SYSTEM
    }

    suspend fun saveThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { preferences ->
            preferences[SettingsKeys.THEME_MODE] = mode.value
        }
    }

    // 读取当前应用语言
    suspend fun getAppLanguage(): AppLanguage {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val locales = context.getSystemService(LocaleManager::class.java).applicationLocales
            return AppLanguage.fromLocaleList(locales)
        }
        return context.settingsDataStore.data.first().let { prefs ->
            AppLanguage.entries.find { it.languageTag == prefs[SettingsKeys.LANGUAGE] } ?: AppLanguage.SYSTEM
        }
    }

    // 设置应用语言：Android 13+ 交系统持久化，其余写入 DataStore
    suspend fun setAppLanguage(language: AppLanguage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(LocaleManager::class.java)
            localeManager.applicationLocales = if (language.languageTag != null) {
                LocaleList.forLanguageTags(language.languageTag)
            } else {
                LocaleList.getEmptyLocaleList()
            }
        } else {
            context.settingsDataStore.edit { preferences ->
                preferences[SettingsKeys.LANGUAGE] = language.languageTag.orEmpty()
            }
        }
    }
}