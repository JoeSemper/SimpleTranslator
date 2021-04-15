package com.joesemper.simpletranslator.presenter

import io.reactivex.Single

interface Interactor<T> {

    fun getData(word: String, fromRemoteSource: Boolean): Single<T>
}