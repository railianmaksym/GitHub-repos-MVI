package com.railian.mobile.githubrepos.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.railian.mobile.githubrepos.GitHubReposApp
import com.railian.mobile.githubrepos.R
import com.railian.mobile.githubrepos.di.search.SearchModule
import com.railian.mobile.githubrepos.di.viewModel.DaggerViewModelFactory
import com.railian.mobile.githubrepos.util.extensions.makeClearableEditText
import com.railian.mobile.githubrepos.util.extensions.openUrlInBrowser
import com.railian.mobile.githubrepos.util.ui.LastItemVisibilityListener
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitHubReposApp.appComponent.getModule(SearchModule()).inject(this)
        searchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        prepareSearchField()
        searchViewModel.repositoryUrl
            .observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty())
                    activity?.openUrlInBrowser(it)
            })
    }

    private fun prepareSearchField() {
        searchText.run {
            makeClearableEditText(
                clearDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_close_dark)!!
            )

            setOnEditorActionListener { _, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchText.text.trim().length < 2) {
                        // showError(R.string.query_validation)
                        return@setOnEditorActionListener false
                    }
                    searchViewModel.searchRepositories(searchText.text.toString().trim())
                    false
                } else
                    return@setOnEditorActionListener true
            }
        }
    }

    private fun prepareRecyclerView() {
        searchResults.adapter = searchViewModel.repositoriesAdapter
        searchResults.addOnScrollListener(object : LastItemVisibilityListener() {
            override fun onLastItemVisible() {
                if (!searchViewModel.isLoadingProcess)
                    searchViewModel.searchRepositories(
                        searchText.text.toString().trim(),
                        isPaging = true
                    )
            }
        })
    }

    override fun onStop() {
        super.onStop()
        searchViewModel.repositoryUrl.removeObservers(viewLifecycleOwner)
    }
}
