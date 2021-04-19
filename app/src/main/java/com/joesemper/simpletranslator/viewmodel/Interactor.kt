package com.joesemper.simpletranslator.viewmodel

import io.reactivex.Single

interface Interactor<T> {

    fun getData(word: String, fromRemoteSource: Boolean): Single<T>
}