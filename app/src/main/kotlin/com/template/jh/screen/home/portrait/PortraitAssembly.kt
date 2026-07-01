package com.template.jh.screen.home.portrait

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.template.jh.R
import com.template.jh.screen.home.portrait.middlePanel.features.FeatureCard
import com.template.jh.screen.home.portrait.topPanel.welcome.WelcomeCard

@Composable
fun PortraitAssembly(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        WelcomeCard(modifier = Modifier.fillMaxWidth())

        FeatureCard(
            title = stringResource(R.string.home_feature_1),
            description = stringResource(R.string.home_feature_1_desc),
            modifier = Modifier.fillMaxWidth()
        )

        FeatureCard(
            title = stringResource(R.string.home_feature_2),
            description = stringResource(R.string.home_feature_2_desc),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
