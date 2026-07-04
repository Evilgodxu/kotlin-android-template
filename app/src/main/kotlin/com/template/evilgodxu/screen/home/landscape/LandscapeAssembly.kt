package com.template.evilgodxu.screen.home.landscape

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.template.evilgodxu.screen.home.HomeUiState
import com.template.evilgodxu.screen.home.landscape.left_content.WelcomeCard
import com.template.evilgodxu.screen.home.landscape.right_content.RightContent
import com.template.evilgodxu.screen.home.landscape.top_toolbar.TopToolbar

// 宽屏页面组装入口：决定空间分区的排列方式
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeAssembly(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onThemeModeChange: (String) -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopToolbar(
                themeMode = uiState.themeMode,
                language = uiState.language,
                onThemeModeChange = onThemeModeChange,
                onLanguageChange = onLanguageChange,
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
        ) {
            WelcomeCard(modifier = Modifier.weight(1f))
            RightContent(modifier = Modifier.weight(1f))
        }
    }
}
