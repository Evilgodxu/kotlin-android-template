package com.template.evilgodxu.screen.home.landscape.main_workspace.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R

@Composable
fun SettingsPanel(
    themeMode: String,
    language: String,
    onThemeModeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SettingsDropdown(
            label = stringResource(R.string.settings_theme_title),
            value = when (themeMode) {
                "light" -> stringResource(R.string.theme_light)
                "dark" -> stringResource(R.string.theme_dark)
                else -> stringResource(R.string.theme_system)
            },
            options = listOf(
                Triple("system", stringResource(R.string.theme_system), Icons.Default.BrightnessAuto),
                Triple("light", stringResource(R.string.theme_light), Icons.Default.LightMode),
                Triple("dark", stringResource(R.string.theme_dark), Icons.Default.Bedtime),
            ),
            onOptionSelected = onThemeModeChange,
        )

        SettingsDropdown(
            label = stringResource(R.string.settings_language_title),
            value = when (language) {
                "zh" -> stringResource(R.string.language_chinese)
                "en" -> stringResource(R.string.language_english)
                else -> stringResource(R.string.language_system)
            },
            options = listOf(
                Triple("system", stringResource(R.string.language_system), Icons.Default.BrightnessAuto),
                Triple("zh", stringResource(R.string.language_chinese), Icons.Default.Language),
                Triple("en", stringResource(R.string.language_english), Icons.Default.Language),
            ),
            onOptionSelected = onLanguageChange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDropdown(
    label: String,
    value: String,
    options: List<Triple<String, String, ImageVector>>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier.fillMaxWidth(),
    ) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            singleLine = true,
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { (key, text, icon) ->
                DropdownMenuItem(
                    text = { Text(text) },
                    leadingIcon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                    },
                    onClick = {
                        onOptionSelected(key)
                        expanded = false
                    },
                )
            }
        }
    }
}
