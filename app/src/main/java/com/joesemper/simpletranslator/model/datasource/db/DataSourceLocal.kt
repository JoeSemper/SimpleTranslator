package com.joesemper.simpletranslator.model.datasource.db

import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import com.joesemper.simpletranslator.model.datasource.remote.RoomDataBaseImplementation
import io.reactivex.Single

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> = remoteProvider.getData(word)
}