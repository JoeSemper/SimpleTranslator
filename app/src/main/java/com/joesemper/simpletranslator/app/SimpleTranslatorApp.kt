package com.joesemper.simpletranslator.app

import android.app.Application
import com.joesemper.simpletranslator.di.AppComponent
import com.joesemper.simpletranslator.di.DaggerAppComponent

class SimpleTranslatorApp: Application() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: SimpleTranslatorApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)

    }



}