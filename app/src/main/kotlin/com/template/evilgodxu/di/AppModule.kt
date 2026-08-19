package com.template.evilgodxu.di

import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.screens.home.HomeViewModel
import com.template.evilgodxu.screens.settings.SettingsViewModel
import com.template.evilgodxu.utils.localization.LocalizationManager
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// Koin 模块：注册数据层与 ViewModel
val appModule = module {
    single { SettingsRepository(get()) }
    single { LocalizationManager(get(), get()) }
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
}
