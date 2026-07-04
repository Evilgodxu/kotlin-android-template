# 横屏首页布局重设计

## 目标
按照 Google Material 3 大屏规范，将横屏首页从「TopBar + 左右双栏」改造为「左侧 Navigation Rail + 右侧内容区」，去除 TopBar，使内容更现代、更美观、空间利用率更高。

## 当前问题
1. 顶部 TopAppBar 独占一行，挤压了内容展示空间。
2. 左侧 WelcomeCard 与右侧 FeatureCard 列表结构松散，视觉层次弱。
3. 设置入口（主题/语言）挤在 TopBar 右侧，宽屏下显得局促。

## 设计方案

### 整体结构
- **无 TopBar**：标题不再通过 TopAppBar 展示，页面顶部完全留给内容。
- **左侧 Navigation Rail**：固定 72dp 宽垂直导航栏，仅保留两项：
  - 主页
  - 设置
- **右侧内容区**：根据左侧选中项切换显示内容。

### 导航栏
- 使用 Material 3 `NavigationRail` 组件。
- 顶部放置应用 Logo/首字母。
- 「主页」与「设置」垂直排列，使用图标 + 标签形式。
- 选中项使用 `NavigationRailItem` 默认选中样式（容器色 + primary 图标/文字）。

### 右侧内容
- **主页态**：
  - 大标题：「欢迎使用」
  - 副标题：「简洁美观的自适应示例应用」
  - 2 列功能卡片网格展示三项功能：自适应布局、多语言支持、主题切换。
  - 卡片使用 Material 3 `Card` + 圆角 + 柔和阴影。
- **设置态**：
  - 标题：「设置」
  - 主题设置下拉菜单
  - 语言设置下拉菜单

### 配色与字体
- 复用现有 Material 3 主题配色（[Color.kt](file:///c:/Android/android-template/app/src/main/kotlin/com/template/evilgodxu/theme/Color.kt)）。
- 卡片背景使用 `surfaceContainerLow`，选中态使用 `secondaryContainer`。
- 标题使用 `headlineMedium`，正文使用 `bodyLarge`/`bodyMedium`。

## 组件结构
```
screen/home/landscape/
├── LandscapeAssembly.kt          // 页面组装入口
├── left_panel/
│   ├── LandscapeTab.kt           // 横屏 tab 枚举（归属左侧导航区）
│   └── LeftPanel.kt              // 左侧导航面板
└── main_workspace/
    ├── MainWorkspace.kt          // 右侧主工作区
    ├── home_summary/
    │   ├── HomeSummary.kt        // 主页摘要（标题 + 功能网格）
    │   └── feature_card/
    │       └── FeatureCard.kt    // 功能卡片
    └── settings/
        └── SettingsPanel.kt      // 设置面板（主题/语言下拉）
```

## 数据流
- `HomeUiState` 新增 `selectedTab: LandscapeTab = LandscapeTab.HOME`。
- `HomeViewModel` 新增 `selectTab(tab: LandscapeTab)` 方法。
- 主题/语言切换保持现有 `setThemeMode` / `setLanguage` 逻辑不变。

## 文件变更清单
- 新增：
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/LandscapeTab.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/left_panel/LeftPanel.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/main_workspace/MainWorkspace.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/main_workspace/home_summary/HomeSummary.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/main_workspace/home_summary/feature_card/FeatureCard.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/main_workspace/settings/SettingsPanel.kt`
- 修改：
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/landscape/LandscapeAssembly.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/HomeUiState.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/HomeViewModel.kt`
  - `app/src/main/kotlin/com/template/evilgodxu/screen/home/HomeScreen.kt`
- 删除：
  - 旧 `landscape/top_toolbar/`、`landscape/left_content/`、`landscape/right_content/` 目录及组件
  - 旧 `landscape/content/`、`landscape/navigation/` 目录及组件

## 实现注意事项
1. 保持 SOMA 架构：Screen 负责路由，Assembly 负责布局组合，子组件按区域拆分。
2. 竖屏 `PortraitAssembly` 不受影响，保持原有实现。
3. 导航栏选中状态通过 `HomeUiState.selectedTab` 驱动，避免在组件内部维护状态。
4. 设置内容复用现有下拉逻辑，但改为在右侧内容区垂直排列。
5. 功能卡片网格使用 `LazyVerticalGrid` 或固定 2 列布局，根据内容高度自适应。
