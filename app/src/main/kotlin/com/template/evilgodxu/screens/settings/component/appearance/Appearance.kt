package com.template.evilgodxu.screens.settings.component.appearance

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import com.template.evilgodxu.R
import com.template.evilgodxu.data.settings.ThemeMode
import com.template.evilgodxu.screens.settings.component.clickableItem.SettingsClickableItem
import com.template.evilgodxu.ui.section.SectionCard

// 外观设置项
@Composable
fun Appearance(themeMode: ThemeMode, onThemeClick: (Offset) -> Unit) {
    SectionCard(title = stringResource(R.string.settings_section_appearance)) {
        SettingsClickableItem(
            icon = R.drawable.ic_palette,
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