package com.joesemper.simpletranslator.di

import androidx.room.Room
import com.joesemper.model.data.DataModel
import com.joesemper.repository.datasource.DataSource
import com.joesemper.repository.datasource.DataSourceLocal
import com.joesemper.repository.datasource.RetrofitImplementation
import com.joesemper.repository.datasource.RoomDataBaseImplementation
import com.joesemper.repository.room.HistoryDataBase
import com.joesemper.simpletranslator.view.history.HistoryInteractor
import com.joesemper.simpletranslator.view.history.HistoryViewModel
import com.joesemper.simpletranslator.view.main.MainInteractor
import com.joesemper.simpletranslator.view.main.MainViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<DataSource<List<DataModel>>> { RetrofitImplementation() }
    single<DataSourceLocal<List<DataModel>>> {
        RoomDataBaseImplementation(
            get()
        )
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}