package com.template.evilgodxu.utils.localization

import android.content.Context
import android.content.res.Configuration
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

// 应用语言转 Locale；systemLocale 为跟随系统时的实际语言
fun AppLanguage.toLocale(systemLocale: Locale = Locale.getDefault()): Locale = when (this) {
    AppLanguage.SYSTEM -> systemLocale
    AppLanguage.CHINESE -> Locale.SIMPLIFIED_CHINESE
    AppLanguage.ENGLISH -> Locale.ENGLISH
}

// 语言管理器：以 DataStore 为单一事实源，输出持久化语言对应的 Locale 流
class LocalizationManager(
    private val context: Context,
    settingsRepository: SettingsRepository,
) {
    // 构造时捕获系统语言。此时进程默认语言尚未被应用内切换改写，
    // 确保跟随系统始终解析为真实系统语言
    private val systemLocale: Locale = Locale.getDefault()

    val localeFlow: Flow<Locale> = settingsRepository.appLanguage.map { it.toLocale(systemLocale) }

    // 以指定语言创建本地化上下文，供 Compose 层通过 LocalContext 提供资源
    fun createLocalizedContext(locale: Locale): Context {
        val config = Configuration(context.resources.configuration).apply {
            setLocales(LocaleList(locale))
        }
        return context.createConfigurationContext(config)
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