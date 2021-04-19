package com.joesemper.simpletranslator.view.base

import com.joesemper.simpletranslator.model.data.AppState

interface MvpView {
    fun renderData(appState: AppState)
}