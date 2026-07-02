package com.template.evilgodxu.screen.home.portrait.top_panel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.screen.home.portrait.top_panel.welcome_card.WelcomeCard

// 竖屏顶部空间分区组装入口
@Composable
fun TopPanel(modifier: Modifier = Modifier) {
    WelcomeCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
