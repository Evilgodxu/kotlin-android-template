package com.template.evilgodxu.screens.home.compact.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    themeMode: String,
    language: String,
    onThemeModeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    // 监听配置变化，辅助语言切换后的即时重组。
    LocalConfiguration.current

    // ModalBottomSheet 内部会创建独立的 Dialog/Window，其 LocalContext 被 dialog 的
    // ContextThemeWrapper(composeView.context) 覆盖，导致 stringResource 仍解析为原语言。
    // 在进入 sheet 前捕获已本地化的上下文，并在内容区重新注入。
    val localizedContext = LocalContext.current
    val localizedConfiguration = localizedContext.resources.configuration

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        CompositionLocalProvider(
            LocalContext provides localizedContext,
            LocalConfiguration provides localizedConfiguration,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .navigationBarsPadding(),
            ) {
                SheetHeader()

                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle(stringResource(R.string.settings_theme_title))
                Spacer(modifier = Modifier.height(4.dp))
                OptionItem(
                    label = stringResource(R.string.theme_system),
                    icon = Icons.Default.BrightnessAuto,
                    selected = themeMode == "system",
                    onClick = { onThemeModeChange("system") },
                )
                OptionItem(
                    label = stringResource(R.string.theme_light),
                    icon = Icons.Default.LightMode,
                    selected = themeMode == "light",
                    onClick = { onThemeModeChange("light") },
                )
                OptionItem(
                    label = stringResource(R.string.theme_dark),
                    icon = Icons.Default.Bedtime,
                    selected = themeMode == "dark",
                    onClick = { onThemeModeChange("dark") },
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )

                SectionTitle(stringResource(R.string.settings_language_title))
                Spacer(modifier = Modifier.height(4.dp))
                OptionItem(
                    label = stringResource(R.string.language_system),
                    icon = Icons.Default.BrightnessAuto,
                    selected = language == "system",
                    onClick = { onLanguageChange("system") },
                )
                OptionItem(
                    label = stringResource(R.string.language_chinese),
                    icon = Icons.Default.Language,
                    selected = language == "zh",
                    onClick = { onLanguageChange("zh") },
                )
                OptionItem(
                    label = stringResource(R.string.language_english),
                    icon = Icons.Default.Language,
                    selected = language == "en",
                    onClick = { onLanguageChange("en") },
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SheetHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(R.string.settings_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 8.dp),
    )
}
