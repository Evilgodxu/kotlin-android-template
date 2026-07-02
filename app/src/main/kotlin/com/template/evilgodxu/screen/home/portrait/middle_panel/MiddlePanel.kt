package com.template.evilgodxu.screen.home.portrait.middle_panel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.screen.home.portrait.middle_panel.features.FeatureCard

// 竖屏中间工作区分区组装入口
@Composable
fun MiddlePanel(modifier: Modifier = Modifier) {
    FeatureCard(
        title = "功能特性",
        description = "按规范组织的功能卡片组件",
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
