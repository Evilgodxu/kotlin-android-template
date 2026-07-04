package com.template.evilgodxu.screen.home.landscape.main_workspace.home_summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.R
import com.template.evilgodxu.screen.home.landscape.main_workspace.home_summary.feature_card.FeatureCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeSummary(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Column {
            Text(
                text = stringResource(R.string.home_welcome),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.home_welcome_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 2,
        ) {
            FeatureCard(
                title = stringResource(R.string.home_feature_1),
                description = stringResource(R.string.home_feature_1_desc),
                icon = Icons.Default.DashboardCustomize,
                modifier = Modifier.weight(1f),
            )
            FeatureCard(
                title = stringResource(R.string.home_feature_2),
                description = stringResource(R.string.home_feature_2_desc),
                icon = Icons.Default.Language,
                modifier = Modifier.weight(1f),
            )
            FeatureCard(
                title = stringResource(R.string.home_feature_3),
                description = stringResource(R.string.home_feature_3_desc),
                icon = Icons.Default.Palette,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
