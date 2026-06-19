# app 模块目录结构

```
kotlin/com/template/jh/
├── MainActivity.kt
├── MyApplication.kt
├── core/
│   └── utils/localization/
│       └── LanguageManager.kt
├── data/
│   └── repository/
│       └── UserPreferencesRepository.kt
├── di/
│   └── AppModule.kt
├── screens/
│   └── home/
│       ├── HomeScreen.kt
│       ├── HomeUiState.kt
│       ├── HomeViewModel.kt
│       ├── landscape/
│       │   └── ExpandedHomeContent.kt
│       ├── portrait/
│       │   └── CompactHomeContent.kt
│       └── shared/
│           ├── FeatureCard.kt
│           └── WelcomeCard.kt
└── ui/
    ├── adaptive/
    │   └── WindowSizeClass.kt
    ├── navigation/
    │   ├── AppNavHost.kt
    │   └── Screen.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```
