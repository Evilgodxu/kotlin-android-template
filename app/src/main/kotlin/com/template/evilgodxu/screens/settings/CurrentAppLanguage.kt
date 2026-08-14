package com.template.evilgodxu.screens.settings

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.appLanguageFlow
import com.template.evilgodxu.data.settings.getAppLanguage

// 当前应用语言：Android 13+ 读 LocaleManager（语言变更自动重组）；
// Android 12L 及以下读 DataStore 语言流
@Composable
fun rememberCurrentAppLanguage(): AppLanguage {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return remember(configuration) { context.getAppLanguage() }
    }
    val language by context.appLanguageFlow().collectAsStateWithLifecycle(initialValue = AppLanguage.SYSTEM)
    return language
}
