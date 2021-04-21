package com.joesemper.simpletranslator.app

import android.app.Application
import com.joesemper.simpletranslator.di.application
import com.joesemper.simpletranslator.di.mainScreen
import org.koin.core.context.startKoin

class SimpleTranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}