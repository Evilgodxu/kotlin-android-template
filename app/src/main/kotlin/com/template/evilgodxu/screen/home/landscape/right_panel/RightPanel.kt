package com.template.evilgodxu.screen.home.landscape.right_panel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.template.evilgodxu.screen.home.landscape.right_panel.features.FeatureCard

// 宽屏右侧空间分区组装入口
@Composable
fun RightPanel(modifier: Modifier = Modifier) {
    FeatureCard(
        title = "功能特性",
        description = "按规范组织的功能卡片组件",
        modifier = modifier
            .width(320.dp)
            .fillMaxHeight()
            .padding(16.dp)
    )
}
