package com.template.evilgodxu.screen.home.portrait.middle_panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.template.evilgodxu.screen.home.portrait.middle_panel.features.FeatureCard
import com.template.evilgodxu.screen.home.portrait.middle_panel.welcome_card.WelcomeCard

// 竖屏中间工作区：欢迎卡片 + 功能特性
@Composable
fun MiddlePanel(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        WelcomeCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.home_features_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                FeatureCard(
                    title = stringResource(R.string.home_feature_1),
                    description = stringResource(R.string.home_feature_1_desc),
                    icon = Icons.Default.DashboardCustomize,
                    modifier = Modifier.fillMaxWidth(),
                )
                FeatureCard(
                    title = stringResource(R.string.home_feature_2),
                    description = stringResource(R.string.home_feature_2_desc),
                    icon = Icons.Default.Language,
                    modifier = Modifier.fillMaxWidth(),
                )
                FeatureCard(
                    title = stringResource(R.string.home_feature_3),
                    description = stringResource(R.string.home_feature_3_desc),
                    icon = Icons.Default.Palette,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
