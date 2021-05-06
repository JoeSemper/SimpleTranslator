package com.joesemper.repository.datasource

import com.joesemper.repository.convertDataModelSuccessToEntity
import com.joesemper.repository.mapHistoryEntityToSearchResult
import com.joesemper.repository.room.HistoryDao


class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<com.joesemper.model.data.DataModel>> {

    override suspend fun getData(word: String): List<com.joesemper.model.data.DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDb(appState: com.joesemper.model.data.AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}