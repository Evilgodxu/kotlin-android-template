package com.template.evilgodxu.screen.home.landscape

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R
import com.template.evilgodxu.screen.home.HomeUiState
import com.template.evilgodxu.screen.home.landscape.left_panel.LeftPanel
import com.template.evilgodxu.screen.home.landscape.right_panel.RightPanel

// 宽屏页面组装入口：决定空间分区的排列方式
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeAssembly(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) },
                windowInsets = WindowInsets(0, 0, 0, 0),
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
            LeftPanel()
            RightPanel()
        }
    }
}
