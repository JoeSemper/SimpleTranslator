package com.joesemper.simpletranslator.view.history

import com.joesemper.model.data.DataModel
import com.joesemper.repository.datasource.DataSource
import com.joesemper.repository.datasource.DataSourceLocal
import com.joesemper.simpletranslator.viewmodel.Interactor

class HistoryInteractor(
    private val repositoryRemote: DataSource<List<DataModel>>,
    private val repositoryLocal: DataSourceLocal<List<DataModel>>
) : Interactor<com.joesemper.model.data.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.joesemper.model.data.AppState {
        return com.joesemper.model.data.AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}