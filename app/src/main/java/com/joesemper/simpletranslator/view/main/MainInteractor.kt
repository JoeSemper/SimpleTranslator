package com.joesemper.simpletranslator.view.main

import com.joesemper.model.data.DataModel
import com.joesemper.repository.datasource.DataSource
import com.joesemper.repository.datasource.DataSourceLocal
import com.joesemper.simpletranslator.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: DataSource<List<DataModel>>,
    private val localRepository: DataSourceLocal<List<DataModel>>
) : Interactor<com.joesemper.model.data.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.joesemper.model.data.AppState {
        val appState: com.joesemper.model.data.AppState
        if (fromRemoteSource) {
            appState = com.joesemper.model.data.AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDb(appState)
        } else {
            appState = com.joesemper.model.data.AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}