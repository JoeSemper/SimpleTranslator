package com.joesemper.simpletranslator.di

import com.joesemper.simpletranslator.model.datasource.RetrofitImplementation
import com.joesemper.simpletranslator.model.datasource.RoomDataBaseImplementation
import com.joesemper.simpletranslator.view.main.MainInteractor
import com.joesemper.simpletranslator.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single(named(NAME_REMOTE)) { RetrofitImplementation() }
    single(named(NAME_LOCAL)) { RoomDataBaseImplementation() }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}