package com.joesemper.simpletranslator.di

import android.app.Application
import androidx.fragment.app.Fragment
import com.joesemper.simpletranslator.app.SimpleTranslatorApp
import com.joesemper.simpletranslator.view.base.BaseFragment
import com.joesemper.simpletranslator.view.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        InteractorModule::class,
        DataSourceModule::class,
        ViewModelModule::class,
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(translatorApp: SimpleTranslatorApp)
    fun inject(mainFragment: MainFragment)
}