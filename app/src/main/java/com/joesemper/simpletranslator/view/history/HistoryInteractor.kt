package com.joesemper.simpletranslator.view.history

import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import com.joesemper.simpletranslator.model.datasource.DataSourceLocal
import com.joesemper.simpletranslator.viewmodel.Interactor

class HistoryInteractor(
    private val repositoryRemote: DataSource<List<DataModel>>,
    private val repositoryLocal: DataSourceLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}