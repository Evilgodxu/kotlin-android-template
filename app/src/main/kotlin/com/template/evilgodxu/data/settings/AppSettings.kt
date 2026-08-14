package com.template.evilgodxu.data.settings

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 设置 DataStore
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsKeys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
    // Android 12L 及以下无 LocaleManager，语言落 DataStore
    val LANGUAGE = stringPreferencesKey("language")
}

// 应用主题模式
enum class ThemeMode(val value: String) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light");

    companion object {
        fun fromValue(value: String): ThemeMode = entries.find { it.value == value } ?: SYSTEM
    }
}

// 应用语言：Android 13+ 由系统 LocaleManager 管理并持久化
enum class AppLanguage(val languageTag: String?) {
    SYSTEM(null),
    CHINESE("zh"),
    ENGLISH("en");

    companion object {
        fun fromLocaleList(localeList: LocaleList): AppLanguage {
            if (localeList.isEmpty) return SYSTEM
            val tag = localeList[0].toLanguageTag()
            return entries.find { it.languageTag == tag } ?: SYSTEM
        }
    }
}

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)

// 设置状态流：主题模式驱动全局配色
fun Context.settingsFlow(): Flow<SettingsState> = settingsDataStore.data.map { preferences ->
    SettingsState(
        themeMode = ThemeMode.fromValue(preferences[SettingsKeys.THEME_MODE] ?: ThemeMode.SYSTEM.value),
    )
}

fun Context.themeModeFlow(): Flow<ThemeMode> = settingsDataStore.data.map { preferences ->
    ThemeMode.fromValue(preferences[SettingsKeys.THEME_MODE] ?: ThemeMode.SYSTEM.value)
}

suspend fun Context.saveThemeMode(mode: ThemeMode) {
    settingsDataStore.edit { preferences ->
        preferences[SettingsKeys.THEME_MODE] = mode.value
    }
}

// Android 12L 及以下的语言流
fun Context.appLanguageFlow(): Flow<AppLanguage> = settingsDataStore.data.map { preferences ->
    AppLanguage.entries.find { it.languageTag == preferences[SettingsKeys.LANGUAGE] } ?: AppLanguage.SYSTEM
}

// 读取当前应用语言
fun Context.getAppLanguage(): AppLanguage {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val locales = getSystemService(LocaleManager::class.java).applicationLocales
        return AppLanguage.fromLocaleList(locales)
    }
    return AppLanguage.SYSTEM
}

// 设置应用语言：Android 13+ 交系统持久化，其余写入 DataStore
suspend fun Context.setAppLanguage(language: AppLanguage) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager = getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = if (language.languageTag != null) {
            LocaleList.forLanguageTags(language.languageTag)
        } else {
            LocaleList.getEmptyLocaleList()
        }
    } else {
        settingsDataStore.edit { preferences ->
            preferences[SettingsKeys.LANGUAGE] = language.languageTag.orEmpty()
        }
    }
}
