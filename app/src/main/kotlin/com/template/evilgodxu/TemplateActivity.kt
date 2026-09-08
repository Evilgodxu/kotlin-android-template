package com.template.evilgodxu

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.template.evilgodxu.navigation.AppNavHost
import com.template.evilgodxu.theme.MyApplicationTheme
import com.template.evilgodxu.utils.localization.LocalizationManager
import com.template.evilgodxu.utils.localization.ProvideLocalizedContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

// Activity 只做入口：挂载导航图与全局副作用，不持有状态字段、不参与业务
class TemplateActivity : ComponentActivity() {
    private val localizationManager: LocalizationManager by inject()
    private val activityViewModel: TemplateActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // 全局副作用：按窗口方向显隐系统栏
            SystemBarsVisibilityEffect()
            CompositionLocalProvider(LocalTemplateActivityViewModel provides activityViewModel) {
                ProvideLocalizedContext(localizationManager) {
                    TemplateContent()
                }
            }
        }
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
}

// 按窗口方向显隐系统栏的全局副作用；横屏隐藏、竖屏显示
@Composable
private fun SystemBarsVisibilityEffect() {
    val view = LocalView.current
    if (view.isInEditMode) return
    val orientation = LocalConfiguration.current.orientation
    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        val controller = WindowCompat.getInsetsController(window, view)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            controller.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}