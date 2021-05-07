package com.joesemper.simpletranslator.di

import androidx.room.Room
import com.joesemper.model.data.DataModel
import com.joesemper.repository.datasource.DataSource
import com.joesemper.repository.datasource.DataSourceLocal
import com.joesemper.repository.datasource.RetrofitImplementation
import com.joesemper.repository.datasource.RoomDataBaseImplementation
import com.joesemper.repository.room.HistoryDataBase
import com.joesemper.simpletranslator.view.main.MainFragment
import com.joesemper.simpletranslator.view.main.MainInteractor
import com.joesemper.simpletranslator.view.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

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
    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get()) }
        scoped { MainInteractor(get(), get()) }
    }

}
