package com.template.evilgodxu.data.settings

import android.content.Context
import android.os.LocaleList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

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