package com.joesemper.simpletranslator.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.presenter.Presenter

abstract class BaseFragment<T : AppState>(@LayoutRes layoutId: Int) : Fragment(layoutId), MvpView {

    protected lateinit var presenter: Presenter<T, MvpView>

    protected abstract fun createPresenter(): Presenter<T, MvpView>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}