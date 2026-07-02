package com.template.evilgodxu.screen.home.portrait

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R
import com.template.evilgodxu.screen.home.HomeUiState
import com.template.evilgodxu.screen.home.portrait.middle_panel.MiddlePanel
import com.template.evilgodxu.screen.home.portrait.top_panel.TopPanel

// 竖屏页面组装入口：决定空间分区的排列方式
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitAssembly(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) },
                windowInsets = WindowInsets.statusBars,
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            TopPanel()
            MiddlePanel()
        }
    }
}
