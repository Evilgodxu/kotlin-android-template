package com.template.evilgodxu.ui.windowSize

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

// 窗口宽度尺寸类：按 Material 窗口尺寸类阈值划分（compact <600dp、medium 600-839dp、expanded >=840dp）
// 参考：https://developer.android.com/develop/ui/compose/layouts/adaptive/window-size-classes
enum class WindowSizeClass {
    Compact,
    Medium,
    Expanded,
}

// 读取当前窗口宽度尺寸类，配置变化时自动重组（规范 13：显示内容按窗口尺寸类分发）
@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val widthDp = LocalConfiguration.current.screenWidthDp
    return when {
        widthDp >= 840 -> WindowSizeClass.Expanded
        widthDp >= 600 -> WindowSizeClass.Medium
        else -> WindowSizeClass.Compact
    }
}