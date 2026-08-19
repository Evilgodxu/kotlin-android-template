package com.template.evilgodxu.utils.localization

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.data.settings.AppLanguage
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 应用语言转 Locale
fun AppLanguage.toLocale(): Locale = when (this) {
    AppLanguage.SYSTEM -> Locale.getDefault()
    AppLanguage.CHINESE -> Locale.SIMPLIFIED_CHINESE
    AppLanguage.ENGLISH -> Locale.ENGLISH
}

// 语言管理器：驱动 Compose 层语言流，并同步 app/activity 层 Resources
class LocalizationManager(
    private val context: Context,
    private val settingsRepository: SettingsRepository,
) {
    val localeFlow: Flow<Locale> = settingsRepository.appLanguage.map { it.toLocale() }

    // 以指定语言创建本地化上下文
    fun createLocalizedContext(locale: Locale): Context {
        val config = Configuration(context.resources.configuration).apply {
            setLocales(LocaleList(locale))
        }
        return context.createConfigurationContext(config)
    }

    // 同步更新 app 层 Resources 与默认 Locale，使 Dialog/Toast 等跟随语言
    fun applyAppLocale(locale: Locale) {
        applyToResources(context.applicationContext.resources, locale)
        Locale.setDefault(locale)
    }

    private fun applyToResources(resources: Resources, locale: Locale) {
        val config = Configuration(resources.configuration).apply {
            setLocales(LocaleList(locale))
        }
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

// 提供本地化 Context 的 Composable，语言切换无需重建 Activity
@Composable
fun ProvideLocalizedContext(
    localizationManager: LocalizationManager,
    content: @Composable () -> Unit,
) {
    val locale by localizationManager.localeFlow.collectAsState(
        initial = LocalLocale.current.platformLocale,
    )
    val localizedContext = localizationManager.createLocalizedContext(locale)
    CompositionLocalProvider(LocalContext provides localizedContext) {
        content()
    }
}