package com.template.evilgodxu.screens.home.expanded.sidebar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R

// 导航区组装器：侧边栏导航项列表
@Composable
fun SidebarArea(
    selectedTab: SidebarTab,
    onTabSelected: (SidebarTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        header = { Spacer(modifier = Modifier.height(32.dp)) },
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        NavigationRailItem(
            selected = selectedTab == SidebarTab.HOME,
            onClick = { onTabSelected(SidebarTab.HOME) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.home_title),
                )
            },
            label = { Text(stringResource(R.string.home_title)) },
        )
        NavigationRailItem(
            selected = selectedTab == SidebarTab.SETTINGS,
            onClick = { onTabSelected(SidebarTab.SETTINGS) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings_title),
                )
            },
            label = { Text(stringResource(R.string.settings_title)) },
        )
    }
}
