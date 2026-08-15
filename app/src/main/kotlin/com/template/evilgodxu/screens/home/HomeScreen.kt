package com.template.evilgodxu.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.template.evilgodxu.screens.home.compact.CompactAssembly

// 紧凑布局页面入口
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onOpenSettings: () -> Unit,
) {
    CompactAssembly(
        modifier = modifier,
        onOpenSettings = onOpenSettings,
    )
}