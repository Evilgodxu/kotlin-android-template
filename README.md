# App Template

一个基于 Jetpack Compose 的 Android 应用模板项目，采用 MVVM + 单向数据流架构，开箱即用。

## 功能特性

- **自适应布局** — 根据设备屏幕宽度自动切换紧凑/展开布局
- **多语言支持** — 支持简体中文、英文与跟随系统，应用内实时切换
- **主题切换** — 浅色 / 深色 / 跟随系统，运行时切换
- **边缘到边缘** — 支持 Edge-to-Edge 显示
- **依赖注入** — 基于 Koin，零模板代码
- **状态持久化** — DataStore 存储主题、语言等用户偏好

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Kotlin 2.4 |
| UI | Jetpack Compose + Material 3 |
| 架构 | MVVM + UDF（单向数据流） |
| 依赖注入 | Koin 4.2 |
| 导航 | Navigation 2.9 (类型安全) |
| 状态管理 | DataStore 1.2 + StateFlow |
| 异步处理 | Kotlin Coroutines + Flow |
| 序列化 | Kotlin Serialization |
| 自适应 | Material 3 Adaptive |
| 构建 | AGP 9.2 + Gradle 9.6 |

## 环境要求

- Android SDK：minSdk 32 / targetSdk 37 / compileSdk 37
- JDK 21
- Kotlin 2.4.0
- AGP 9.2.1
- Gradle 9.6.0

## 项目结构

```
app/src/main/kotlin/com/template/jh/
├── MainActivity.kt              # 主 Activity，Edge-to-Edge、主题/语言初始化
├── MyApplication.kt             # 应用入口，Koin 初始化
├── core/
│   └── utils/
│       └── localization/
│           └── LanguageManager.kt      # 语言切换管理
├── data/
│   └── repository/
│       └── UserPreferencesRepository.kt # DataStore 用户偏好仓库
├── di/
│   └── AppModule.kt                    # Koin 依赖注入模块
├── screens/
│   └── home/
│       ├── HomeScreen.kt               # 主屏幕（自适应布局入口）
│       ├── HomeViewModel.kt            # 主屏幕 ViewModel
│       ├── HomeUiState.kt              # 主屏幕 UI 状态
│       ├── portrait/
│       │   └── CompactHomeContent.kt   # 紧凑布局（竖屏/小屏）
│       ├── landscape/
│       │   └── ExpandedHomeContent.kt  # 展开布局（横屏/大屏）
│       └── shared/
│           ├── FeatureCard.kt          # 功能特性卡片
│           └── WelcomeCard.kt          # 欢迎卡片
└── ui/
    ├── adaptive/
    │   └── WindowSizeClass.kt          # 窗口尺寸分类
    ├── navigation/
    │   ├── AppNavHost.kt               # 导航宿主
    │   └── Screen.kt                   # 导航目标定义（类型安全）
    └── theme/
        ├── Color.kt                    # 完整 Material 3 调色板
        ├── Theme.kt                    # 主题（浅色/深色/动态取色）
        └── Type.kt                     # 排版样式
```

## 发布版构建配置

项目配置了完整的发布版构建流程，包括签名和代码混淆：

### 签名配置

发布版使用 `local.properties` 管理密钥信息：

```properties
KEYSTORE_PASSWORD=your_keystore_password
KEY_ALIAS=your_key_alias
KEY_PASSWORD=your_key_password
```

签名文件 `jh.keystore` 需放置于项目根目录。

### 构建类型

| 类型 | 特性 |
|------|------|
| **Release** | 启用代码混淆、资源压缩、PNG 优化、签名打包 |
| **Debug** | 关闭调试标志，启用 ProGuard 规则 |

### 构建命令

```bash
# 构建发布版 APK
./gradlew assembleRelease

# 构建发布版 AAB
./gradlew bundleRelease
```

### 构建优化

- Gradle 并行构建 + 构建缓存 + 按需配置
- R8 全模式混淆优化
- Kotlin 增量编译
- 只打包 `arm64-v8a` 架构
- 阿里云 Maven 镜像加速依赖下载

## 构建与运行

1. 克隆仓库

```bash
git clone https://github.com/Evilgodxu/kotlin-android-template.git
```

2. 使用 Android Studio 打开项目

3. 配置签名（可选，用于发布版构建）
   - 在项目根目录创建 `local.properties` 文件
   - 添加密钥配置信息

4. 同步 Gradle 后直接运行

## 许可证

[MIT License](LICENSE)
