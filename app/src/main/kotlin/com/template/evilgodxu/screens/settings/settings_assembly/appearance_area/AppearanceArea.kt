package com.template.evilgodxu.screens.settings.settings_assembly.appearance_area

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.screens.settings.settings_assembly.component.clickableItem.SettingsClickableItem
import com.template.evilgodxu.screens.settings.settings_assembly.component.section.SettingsSection

// 外观分区
@Composable
fun AppearanceArea(themeMode: ThemeMode, onThemeClick: (Offset) -> Unit) {
    SettingsSection(title = stringResource(R.string.settings_section_appearance)) {
        SettingsClickableItem(
            icon = Icons.Default.Palette,
            title = stringResource(R.string.settings_theme_title),
            subtitle = when (themeMode) {
                ThemeMode.SYSTEM -> stringResource(R.string.theme_system)
                ThemeMode.DARK -> stringResource(R.string.theme_dark)
                ThemeMode.LIGHT -> stringResource(R.string.theme_light)
            },
            onClick = {},
            onClickWithPosition = { position ->
                onThemeClick(position)
            },
        )
    }
}
