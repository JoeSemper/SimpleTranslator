package com.joesemper.simpletranslator.view.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.joesemper.simpletranslator.R
import com.joesemper.simpletranslator.model.data.AppState
import com.joesemper.simpletranslator.model.data.DataModel
import com.joesemper.simpletranslator.utils.network.isOnline
import com.joesemper.simpletranslator.utils.ui.AlertDialogFragment
import com.joesemper.simpletranslator.viewmodel.BaseViewModel
import com.joesemper.simpletranslator.viewmodel.Interactor
import kotlinx.android.synthetic.main.loading_layout.*

abstract class BaseFragment<T : AppState, I: Interactor<T>> : Fragment() {

    abstract val model: BaseViewModel<T>

    protected var isNetWorkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetWorkAvailable = isOnline(requireContext())
    }

    override fun onResume() {
        super.onResume()
        isNetWorkAvailable = isOnline(requireContext())
        if (!isNetWorkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
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
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error), appState.error.message)
            }
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        val fragmentManager = requireActivity().supportFragmentManager
        AlertDialogFragment.newInstance(title, message).show(fragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return requireActivity().supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    private fun showViewWorking() {
        loading_frame_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = View.VISIBLE
    }

    abstract fun setDataToAdapter(data: List<DataModel>)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }
}