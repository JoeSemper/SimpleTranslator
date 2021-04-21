package com.joesemper.simpletranslator.view.main

import com.joesemper.simpletranslator.di.NAME_LOCAL
import com.joesemper.simpletranslator.di.NAME_REMOTE
import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import com.joesemper.simpletranslator.viewmodel.Interactor
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: DataSource<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: DataSource<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Single<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}