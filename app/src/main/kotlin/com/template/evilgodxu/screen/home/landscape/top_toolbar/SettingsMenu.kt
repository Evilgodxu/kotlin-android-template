package com.template.evilgodxu.screen.home.landscape.top_toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsMenu(
    themeMode: String,
    language: String,
    onThemeModeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SettingsDropdown(
            label = stringResource(R.string.settings_theme_title),
            value = when (themeMode) {
                "light" -> stringResource(R.string.theme_light)
                "dark" -> stringResource(R.string.theme_dark)
                else -> stringResource(R.string.theme_system)
            },
            options = listOf(
                "system" to stringResource(R.string.theme_system) to Icons.Default.BrightnessAuto,
                "light" to stringResource(R.string.theme_light) to Icons.Default.LightMode,
                "dark" to stringResource(R.string.theme_dark) to Icons.Default.Bedtime,
            ),
            onOptionSelected = onThemeModeChange,
            modifier = Modifier.width(140.dp),
        )

        SettingsDropdown(
            label = stringResource(R.string.settings_language_title),
            value = when (language) {
                "zh" -> stringResource(R.string.language_chinese)
                "en" -> stringResource(R.string.language_english)
                else -> stringResource(R.string.language_system)
            },
            options = listOf(
                "system" to stringResource(R.string.language_system) to Icons.Default.BrightnessAuto,
                "zh" to stringResource(R.string.language_chinese) to Icons.Default.Language,
                "en" to stringResource(R.string.language_english) to Icons.Default.Language,
            ),
            onOptionSelected = onLanguageChange,
            modifier = Modifier.width(140.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDropdown(
    label: String,
    value: String,
    options: List<Pair<Pair<String, String>, androidx.compose.ui.graphics.vector.ImageVector>>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, style = MaterialTheme.typography.labelSmall) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            singleLine = true,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize(),
        ) {
            options.forEach { (option, icon) ->
                val (key, text) = option
                OptionItem(
                    label = text,
                    icon = icon,
                    selected = value == text,
                    onClick = {
                        onOptionSelected(key)
                        expanded = false
                    },
                )
            }
        }
    }
}
