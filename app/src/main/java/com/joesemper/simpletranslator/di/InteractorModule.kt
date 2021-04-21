package com.joesemper.simpletranslator.di

import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import com.joesemper.simpletranslator.view.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: DataSource<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: DataSource<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}