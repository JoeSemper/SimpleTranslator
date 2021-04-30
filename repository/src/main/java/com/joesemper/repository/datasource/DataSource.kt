package com.joesemper.repository.datasource

interface DataSource<T> {

    suspend fun getData(word: String): T
}