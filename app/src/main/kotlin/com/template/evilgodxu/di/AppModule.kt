package com.template.evilgodxu.di

import com.template.evilgodxu.screens.home.HomeViewModel
import com.template.evilgodxu.screens.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// Koin 模块：注册 ViewModel
val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
}
