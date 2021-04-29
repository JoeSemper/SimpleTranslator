package com.joesemper.simpletranslator.model.datasource

import com.joesemper.simpletranslator.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDb(appState: AppState)
}