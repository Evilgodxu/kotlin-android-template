package com.template.evilgodxu.screen.home.landscape.main_workspace.sidebar

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

@Composable
fun LeftPanel(
    selectedTab: LandscapeTab,
    onTabSelected: (LandscapeTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        header = { Spacer(modifier = Modifier.height(32.dp)) },
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        NavigationRailItem(
            selected = selectedTab == LandscapeTab.HOME,
            onClick = { onTabSelected(LandscapeTab.HOME) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.home_title),
                )
            },
            label = { Text(stringResource(R.string.home_title)) },
        )
        NavigationRailItem(
            selected = selectedTab == LandscapeTab.SETTINGS,
            onClick = { onTabSelected(LandscapeTab.SETTINGS) },
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
