package com.template.evilgodxu

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.appLanguageFlow
import com.template.evilgodxu.navigation.AppNavHost
import com.template.evilgodxu.theme.MyApplicationTheme
import com.template.evilgodxu.theme.ProvideWindowSizeClass
import java.util.Locale
import kotlinx.coroutines.flow.map

class TemplateActivity : ComponentActivity() {
    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupSystemBars()

        setContent {
            // Android 13+ 由系统按应用语言提供资源并自动重建界面；
            // Android 12L 及以下从 DataStore 读语言并注入本地化上下文
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                TemplateContent()
            } else {
                val context = LocalContext.current
                val localizedLocaleFlow = remember {
                    context.appLanguageFlow().map { resolveLocale(it) }
                }
                val locale by localizedLocaleFlow
                    .collectAsState(initial = LocalLocale.current.platformLocale)
                CompositionLocalProvider(LocalContext provides createLocalizedContext(locale)) {
                    TemplateContent()
                }
            }
        }
    }

    @Composable
    private fun TemplateContent() {
        ProvideWindowSizeClass {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AppNavHost()
                }
            }
        }
    }

    // 应用语言转 Locale
    private fun resolveLocale(appLanguage: AppLanguage): Locale = when (appLanguage) {
        AppLanguage.CHINESE -> Locale.SIMPLIFIED_CHINESE
        AppLanguage.ENGLISH -> Locale.ENGLISH
        AppLanguage.SYSTEM -> Locale.getDefault()
    }

    // 以指定语言创建本地化上下文
    private fun createLocalizedContext(locale: Locale): Context {
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        return createConfigurationContext(config)
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateSystemBarsVisibility()
    }
}
