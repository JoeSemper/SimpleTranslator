package com.joesemper.simpletranslator.view.main

import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.datasource.db.DataSourceLocal
import com.joesemper.simpletranslator.model.datasource.remote.DataSourceRemote
import com.joesemper.simpletranslator.presenter.Presenter
import com.joesemper.simpletranslator.rx.SchedulerProvider
import com.joesemper.simpletranslator.view.base.MvpView
import io.reactivex.disposables.CompositeDisposable

class MainPresenterImpl<T : AppState, V : MvpView>(
    private val interactor: MainInteractor = MainInteractor(
        remoteRepository = DataSourceRemote(),
        localRepository = DataSourceLocal()),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribe({ appState ->
                    currentView?.renderData(appState)
                }, { error ->
                    currentView?.renderData(AppState.Error(error))
                })
        )
    }
}