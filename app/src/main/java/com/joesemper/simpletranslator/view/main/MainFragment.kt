package com.joesemper.simpletranslator.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.simpletranslator.R
import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.utils.convertMeaningsToString
import com.joesemper.simpletranslator.utils.network.isOnline
import com.joesemper.simpletranslator.view.base.BaseFragment
import com.joesemper.simpletranslator.view.main.adapter.MainAdapter
import com.joesemper.simpletranslator.view.main.description.DescriptionFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<AppState, MainInteractor>() {

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener = OnMainRvItemClickListener()

    override lateinit var model: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = View.inflate(context, R.layout.fragment_main, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
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
        if (dataModel.isNullOrEmpty()) {
            showViewContent()
            showAlertDialog(
                getString(R.string.dialog_tittle_sorry),
                getString(R.string.empty_server_response_on_success)
            )
        } else {
            showViewContent()
            adapter.setData(dataModel)
        }
    }


    private fun doOnLoading(appState: AppState.Loading) {
        showViewLoading()
        if (appState.progress != null) {
            progress_bar_horizontal.visibility = VISIBLE
            progress_bar_round.visibility = GONE
            progress_bar_horizontal.progress = appState.progress
        } else {
            progress_bar_horizontal.visibility = GONE
            progress_bar_round.visibility = VISIBLE
        }
    }

    private fun doOnError(appState: AppState.Error) {
        showErrorScreen(appState.error.message)
    }

    private fun showErrorScreen(error: String?) {
        showViewContent()
        showAlertDialog(getString(R.string.error), error)
    }

    private fun showViewContent() {
        group_success.visibility = VISIBLE
        group_loading.visibility = GONE
    }

    private fun showViewLoading() {
        group_loading.visibility = VISIBLE
        group_success.visibility = GONE
    }

    inner class OnMainRvItemClickListener : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToDescriptionFragment(
                    data.meanings?.first()?.imageUrl!!,
                    data.text!!,
                    convertMeaningsToString(data.meanings),
                ))
        }
    }

    private fun initViewModel() {
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this as LifecycleOwner, { renderData(it) })
    }

    private fun initViews() {
        initRV()
        setOnSearchClickListener()
    }

    private fun initRV() {
        rv_main.layoutManager = LinearLayoutManager(context)
        rv_main.adapter = adapter
    }

    private fun setOnSearchClickListener() {
        text_input_layout_search.setEndIconOnClickListener {
            hideKeyboard(text_input_search)
            val word = text_input_search.text.toString()
            isNetWorkAvailable = isOnline(requireContext())
            if (isNetWorkAvailable) {
                model.getData(word, isNetWorkAvailable)
            } else {
                showNoInternetConnectionDialog()
            }
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