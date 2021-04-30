package com.joesemper.repository.datasource

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDb(appState: com.joesemper.model.data.AppState)
}