package com.template.evilgodxu.screens.home.expanded

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.screens.home.HomeUiState
import com.template.evilgodxu.screens.home.expanded.sidebar.SidebarArea
import com.template.evilgodxu.screens.home.expanded.summary.SummaryArea
import com.template.evilgodxu.screens.home.expanded.sidebar.SidebarTab

// 展开布局组装器：侧边栏 + 内容区（按 Tab 切换）
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedAssembly(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onOpenSettings: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding),
        ) {
            SidebarArea(
                selectedTab = uiState.selectedTab,
                onTabSelected = { tab ->
                    if (tab == SidebarTab.SETTINGS) onOpenSettings()
                },
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (uiState.selectedTab) {
                    SidebarTab.HOME -> SummaryArea()

                    SidebarTab.SETTINGS -> SummaryArea()
                }
            }
        }
    }
}
