package com.template.evilgodxu

import android.app.Application
import com.template.evilgodxu.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.dsl.koinConfiguration

class TemplateApplication : Application(), KoinStartup {

    override fun onKoinStartup() = koinConfiguration {
        androidLogger()
        androidContext(this@TemplateApplication)
        modules(appModule)
    }
}
