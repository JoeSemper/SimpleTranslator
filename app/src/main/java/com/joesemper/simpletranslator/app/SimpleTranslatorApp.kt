package com.joesemper.simpletranslator.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SimpleTranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { androidContext(applicationContext) }
    }
}