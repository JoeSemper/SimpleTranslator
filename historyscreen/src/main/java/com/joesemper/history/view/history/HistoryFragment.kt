package com.joesemper.history.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.history.R
import com.joesemper.history.view.history.adapter.HistoryAdapter
import com.joesemper.model.data.AppState
import com.joesemper.model.data.DataModel
import com.joesemper.simpletranslator.utils.ui.viewById
import com.joesemper.simpletranslator.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.scope.currentScope

class HistoryFragment : BaseFragment<AppState, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    private val historyRecycler by viewById<RecyclerView>(R.id.rv_history)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_history, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionbarHomeButtonAsUp()
        initViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        with(requireActivity() as AppCompatActivity) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initViewModel() {
        if (historyRecycler.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        injectDependencies()
        val viewModel: HistoryViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this@HistoryFragment as LifecycleOwner, { renderData(it) })
    }

    private fun initViews() {
        historyRecycler.adapter = adapter
    }

}