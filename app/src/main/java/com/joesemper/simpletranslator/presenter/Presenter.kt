package com.joesemper.simpletranslator.presenter

import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.view.base.MvpView

interface Presenter<T : AppState, V : MvpView> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}