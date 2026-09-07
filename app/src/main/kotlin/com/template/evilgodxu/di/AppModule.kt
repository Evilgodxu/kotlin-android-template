package com.template.evilgodxu.di

import android.content.Context
import android.content.pm.PackageManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.template.evilgodxu.data.repository.DataStoreSettingsRepository
import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.screens.home.HomeViewModel
import com.template.evilgodxu.screens.settings.SettingsViewModel
import com.template.evilgodxu.utils.localization.LocalizationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// Koin 模块：注册数据层与 ViewModel
val appModule = module {
    // 数据源经构造注入仓库，便于单元测试替换
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            androidContext().preferencesDataStoreFile("settings")
        }
    }
    single<SettingsRepository> { DataStoreSettingsRepository(get()) }
    single { LocalizationManager(get(), get()) }
    single { appVersionName(androidContext()) }
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
}

// 应用版本号
private fun appVersionName(context: Context): String =
    context.packageManager
        .getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0L))
        .versionName.orEmpty()