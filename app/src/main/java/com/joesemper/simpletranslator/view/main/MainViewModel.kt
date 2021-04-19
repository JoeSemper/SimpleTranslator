package com.joesemper.simpletranslator.view.main

import androidx.lifecycle.LiveData
import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.utils.parseSearchResults
import com.joesemper.simpletranslator.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable

class MainViewModel (private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribe({ state ->
                    appState = parseSearchResults(state)
                    liveDataForViewToObserve.value = appState
                }, {
                    liveDataForViewToObserve.value = AppState.Error(it)
                })
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit = {
        liveDataForViewToObserve.value = AppState.Loading(null)
    }



}