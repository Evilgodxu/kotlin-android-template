package com.template.evilgodxu.di

import com.template.evilgodxu.data.repository.UserPreferencesRepository
import com.template.evilgodxu.screens.home.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::UserPreferencesRepository)
    viewModelOf(::HomeViewModel)
}
