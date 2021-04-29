package com.joesemper.simpletranslator.view.main

import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import com.joesemper.simpletranslator.model.datasource.DataSourceLocal
import com.joesemper.simpletranslator.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: DataSource<List<DataModel>>,
    private val localRepository: DataSourceLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDb(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}