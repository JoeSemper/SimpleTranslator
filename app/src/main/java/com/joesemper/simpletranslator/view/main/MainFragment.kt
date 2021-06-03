package com.joesemper.simpletranslator.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.joesemper.model.data.AppState
import com.joesemper.model.data.DataModel
import com.joesemper.simpletranslator.R
import com.joesemper.simpletranslator.di.injectDependencies
import com.joesemper.simpletranslator.utils.convertMeaningsToString
import com.joesemper.simpletranslator.utils.network.OnlineLiveData
import com.joesemper.simpletranslator.utils.ui.viewById
import com.joesemper.simpletranslator.view.base.BaseFragment
import com.joesemper.simpletranslator.view.main.adapter.MainAdapter
import org.koin.android.scope.currentScope

private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyscreen"

class MainFragment : BaseFragment<AppState, MainInteractor>() {

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener = OnMainRvItemClickListener()

    override lateinit var model: MainViewModel

    private lateinit var splitInstallManager: SplitInstallManager

    private val mainRecyclerView by viewById<RecyclerView>(R.id.rv_main)
    private val textInputLayout by viewById<TextInputLayout>(R.id.text_input_layout_search)
    private val textInputSearch by viewById<TextInputEditText>(R.id.text_input_search)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionbarHomeButtonAsUpDisabled()
        initViewModel()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> runHistoryScreen()
            R.id.menu_settings -> runSettings()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    inner class OnMainRvItemClickListener : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToDescriptionFragment(
                    data.meanings?.first()?.imageUrl!!,
                    data.text!!,
                    convertMeaningsToString(data.meanings!!),
                )
            )
        }
    }

    private fun initViewModel() {
        if (mainRecyclerView.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        injectDependencies()
        val viewModel: MainViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this as LifecycleOwner, { renderData(it) })
    }

    private fun initViews() {
        initRV()
        setOnSearchClickListener()
    }

    private fun initRV() {
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mainRecyclerView.adapter = adapter
    }

    private fun runHistoryScreen(): Boolean {
        splitInstallManager = SplitInstallManagerFactory.create(requireContext())
        val request =
            SplitInstallRequest
                .newBuilder()
                .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener {
                val fragment = Class.forName("com.joesemper.history.view.history.HistoryFragment")
                    .newInstance() as Fragment

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, fragment)
                    .commit()

//                        view?.findNavController()?.navigate(
//                            MainFragmentDirections.actionMainFragmentToHistoryFragment()
//                        )
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Couldn't download feature: " + it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        return true
    }

    @SuppressLint("InlinedApi")
    private fun runSettings(): Boolean {
        startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 42)
        return true
    }

    private fun setOnSearchClickListener() {
        textInputLayout.setEndIconOnClickListener {
            hideKeyboard(textInputSearch)
            val word = textInputSearch.text.toString()

            OnlineLiveData(requireContext()).observe(
                this as LifecycleOwner,
                Observer<Boolean> { isNetWorkAvailable ->
                    if (isNetWorkAvailable) {
                        model.getData(word, isNetWorkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
                })
        }
    }

    private fun setActionbarHomeButtonAsUpDisabled() {
        with(requireActivity() as AppCompatActivity) {
            supportActionBar?.setHomeButtonEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
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