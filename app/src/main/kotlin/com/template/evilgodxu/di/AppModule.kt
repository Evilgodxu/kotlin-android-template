package com.template.evilgodxu.di

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
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

// 应用版本号：PackageInfoFlags 重载自 API 33 引入，低版本回退旧重载：
// https://developer.android.com/reference/android/content/pm/PackageManager#getPackageInfo(java.lang.String,%20android.content.pm.PackageManager.PackageInfoFlags)
private fun appVersionName(context: Context): String {
    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0L))
    } else {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }
    return packageInfo.versionName.orEmpty()
}