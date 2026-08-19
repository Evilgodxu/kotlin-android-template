package com.template.evilgodxu

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.navigation.AppNavHost
import com.template.evilgodxu.theme.MyApplicationTheme
import com.template.evilgodxu.utils.localization.LocalizationManager
import com.template.evilgodxu.utils.localization.ProvideLocalizedContext
import com.template.evilgodxu.utils.localization.toLocale
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class TemplateActivity : ComponentActivity() {
    private lateinit var windowInsetsController: WindowInsetsControllerCompat
    private val localizationManager: LocalizationManager by inject()

    // 冷启动按持久化语言创建配置上下文，进入界面即正确语言
    override fun attachBaseContext(newBase: Context) {
        val locale = runBlocking { SettingsRepository(newBase).appLanguage.first() }.toLocale()
        val config = Configuration(newBase.resources.configuration).apply {
            setLocales(LocaleList(locale))
        }
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupSystemBars()

        setContent {
            ProvideLocalizedContext(localizationManager) {
                TemplateContent()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateSystemBarsVisibility()
    }

    @Composable
    private fun TemplateContent() {
        MyApplicationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                AppNavHost()
            }
        }
    }

    private fun setupSystemBars() {
        windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        updateSystemBarsVisibility()
    }

    // 横屏隐藏系统栏，竖屏显示
    private fun updateSystemBarsVisibility() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}
