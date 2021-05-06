package com.joesemper.simpletranslator.app

import android.app.Application
import com.joesemper.simpletranslator.di.application
import com.joesemper.simpletranslator.di.historyScreen
import com.joesemper.simpletranslator.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SimpleTranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}