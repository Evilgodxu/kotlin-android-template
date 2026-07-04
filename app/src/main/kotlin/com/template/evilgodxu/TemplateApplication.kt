package com.template.evilgodxu

import android.app.Application
import com.template.evilgodxu.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TemplateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TemplateApplication)
            modules(appModule)
        }
    }
}
