package com.template.evilgodxu.screens.settings.compact.app_info

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R
import com.template.evilgodxu.screens.settings.compact.app_info.component.SettingsSection

private const val GITHUB_URL = "https://github.com/Evilgodxu/kotlin-android-template.git"

@Composable
fun AppInfo(version: String) {
    val context = LocalContext.current
    SettingsSection(title = stringResource(R.string.settings_section_app_info)) {
        Text("Evilgodxu", modifier = Modifier.fillMaxWidth().padding(top = 16.dp), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(stringResource(R.string.settings_version, version), modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
        Row(
            modifier = Modifier.fillMaxWidth().clickable { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL))) }.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Default.Code, null)
            Text(GITHUB_URL, modifier = Modifier.padding(start = 6.dp), color = MaterialTheme.colorScheme.primary)
        }
    }
}
