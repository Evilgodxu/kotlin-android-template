package com.template.evilgodxu.screen.home.landscape.left_panel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.screen.home.landscape.left_panel.welcome_card.WelcomeCard

// 宽屏左侧空间分区组装入口
@Composable
fun LeftPanel(modifier: Modifier = Modifier) {
    WelcomeCard(
        modifier = modifier
            .width(320.dp)
            .fillMaxHeight()
            .padding(16.dp)
    )
}
