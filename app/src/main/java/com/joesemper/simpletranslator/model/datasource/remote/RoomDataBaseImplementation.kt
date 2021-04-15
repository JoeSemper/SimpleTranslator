package com.joesemper.simpletranslator.model.datasource.remote

import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.model.datasource.DataSource
import io.reactivex.Single

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}