package com.template.evilgodxu.navigation

import android.os.SystemClock
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.template.evilgodxu.R
import com.template.evilgodxu.screens.home.HomeScreen
import com.template.evilgodxu.screens.settings.SettingsScreen

// 双击退出确认窗口
private const val EXIT_CONFIRM_WINDOW_MS = 2000L

// 导航宿主：统一走路由栈
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(Home)
    val activity = LocalActivity.current
    val context = LocalContext.current
    var lastRootBackAt by rememberSaveable { mutableLongStateOf(0L) }
    val exitHint = stringResource(R.string.exit_hint)
    val goBack: () -> Unit = {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        } else {
            val now = SystemClock.uptimeMillis()
            if (now - lastRootBackAt <= EXIT_CONFIRM_WINDOW_MS) {
                activity?.finish()
            } else {
                lastRootBackAt = now
                Toast.makeText(context, exitHint, Toast.LENGTH_SHORT).show()
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = goBack,
        modifier = modifier,
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key) { HomeScreen(onOpenSettings = { backStack.add(Settings) }) }
                is Settings -> NavEntry(key) {
                    SettingsScreen(onBack = goBack)
                }
                else -> error("Unknown NavKey: $key")
            }
        },
    )
}