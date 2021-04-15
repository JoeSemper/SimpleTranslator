package com.joesemper.simpletranslator.model.datasource.remote

import com.joesemper.simpletranslator.model.data.DataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Single<List<DataModel>>
}