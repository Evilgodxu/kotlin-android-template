package com.template.evilgodxu.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.template.evilgodxu.screens.home.compact.CompactAssembly
import org.koin.androidx.compose.koinViewModel

// 紧凑布局页面入口
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onOpenSettings: () -> Unit,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by homeViewModel.state.collectAsStateWithLifecycle()
    CompactAssembly(
        modifier = modifier,
        uiState = uiState,
        onOpenSettings = onOpenSettings,
    )
}