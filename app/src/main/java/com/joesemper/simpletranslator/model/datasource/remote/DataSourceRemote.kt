package com.joesemper.simpletranslator.model.datasource.remote

import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import com.joesemper.simpletranslator.model.datasource.db.RetrofitImplementation
import io.reactivex.Single

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> = remoteProvider.getData(word)
}