package com.joesemper.simpletranslator.view.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.simpletranslator.R
import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.presenter.Presenter
import com.joesemper.simpletranslator.view.base.BaseFragment
import com.joesemper.simpletranslator.view.base.MvpView
import com.joesemper.simpletranslator.view.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment<AppState>(R.layout.fragment_main) {

    private var adapter: MainAdapter? = null

    private val onListItemClickListener = OnMainRvItemClickListener()

    override fun createPresenter(): Presenter<AppState, MvpView> {
        return MainPresenterImpl()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnSearchClickListener()
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> doOnSuccess(appState)
            is AppState.Loading -> doOnLoading(appState)
            is AppState.Error -> doOnError(appState)
        }
    }

    private fun doOnSuccess(appState: AppState.Success) {
        val dataModel = appState.data
        if (dataModel == null || dataModel.isEmpty()) {
            showErrorScreen(getString(R.string.empty_server_response_on_success))
        } else {
            showViewSuccess()
            if (adapter == null) {
                rv_main.layoutManager = LinearLayoutManager(context)
                rv_main.adapter = MainAdapter(onListItemClickListener, dataModel)
            } else {
                adapter!!.setData(dataModel)
            }
        }
    }

    private fun doOnLoading(appState: AppState.Loading) {
        showViewLoading()
        if (appState.progress != null) {
            progress_bar_horizontal.visibility = View.VISIBLE
            progress_bar_round.visibility = View.GONE
            progress_bar_horizontal.progress = appState.progress
        } else {
            progress_bar_horizontal.visibility = View.GONE
            progress_bar_round.visibility = View.VISIBLE
        }
    }

    private fun doOnError(appState: AppState.Error) {
        showErrorScreen(appState.error.message)
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        tv_error_main.text = error ?: getString(R.string.undefined_error)
        button_reload.setOnClickListener {
            presenter.getData(text_input_search.text.toString(), true)
        }
    }

    private fun showViewSuccess() {
        group_success.visibility = View.VISIBLE
        group_loading.visibility = View.GONE
        group_error.visibility = View.GONE
    }

    private fun showViewLoading() {
        group_success.visibility = View.GONE
        group_loading.visibility = View.VISIBLE
        group_error.visibility = View.GONE
    }

    private fun showViewError() {
        group_success.visibility = View.GONE
        group_loading.visibility = View.GONE
        group_error.visibility = View.VISIBLE
    }

    inner class OnMainRvItemClickListener : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            Toast.makeText(context, data.text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnSearchClickListener() {
        text_input_layout_search.setEndIconOnClickListener {
            hideKeyboard(text_input_search)
            presenter.getData(text_input_search.text.toString(), true)
        }
    }

    private fun hideKeyboard(view: View) {
        if (view.isFocused) {
            view.clearFocus()
            val manager = getSystemService(requireContext(), InputMethodManager::class.java)
            manager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}