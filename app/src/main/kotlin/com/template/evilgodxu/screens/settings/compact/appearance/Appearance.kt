package com.template.evilgodxu.screens.settings.compact.appearance

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.screens.settings.compact.appearance.component.SettingsClickableItem
import com.template.evilgodxu.screens.settings.compact.appearance.component.SettingsSection

@Composable
fun Appearance(
    themeMode: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    onThemeClick: (Offset) -> Unit,
) {
    SettingsSection(title = stringResource(R.string.settings_section_appearance)) {
        ThemeMode.entries.forEach { mode ->
            SettingsClickableItem(
                icon = Icons.Default.Palette,
                title = themeName(mode),
                subtitle = if (themeMode == mode) stringResource(R.string.settings_selected) else "",
                onClick = { onThemeSelected(mode) },
                onClickWithPosition = { position ->
                    onThemeClick(position)
                    onThemeSelected(mode)
                },
            )
        }
    }
}

@Composable
private fun themeName(mode: ThemeMode): String = when (mode) {
    ThemeMode.SYSTEM -> stringResource(R.string.theme_system)
    ThemeMode.DARK -> stringResource(R.string.theme_dark)
    ThemeMode.LIGHT -> stringResource(R.string.theme_light)
}
