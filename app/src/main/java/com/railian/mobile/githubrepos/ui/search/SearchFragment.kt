package com.railian.mobile.githubrepos.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.zzg
import com.railian.mobile.githubrepos.GitHubReposApp
import com.railian.mobile.githubrepos.R
import com.railian.mobile.githubrepos.data.local.prefs.UserDataStore
import com.railian.mobile.githubrepos.di.search.SearchModule
import com.railian.mobile.githubrepos.di.viewModel.DaggerViewModelFactory
import com.railian.mobile.githubrepos.ui.base.MviView
import com.railian.mobile.githubrepos.ui.search.dataFlow.SearchAction
import com.railian.mobile.githubrepos.ui.search.dataFlow.SearchDataFlow
import com.railian.mobile.githubrepos.util.extensions.hide
import com.railian.mobile.githubrepos.util.extensions.makeClearableEditText
import com.railian.mobile.githubrepos.util.extensions.openUrlInBrowser
import com.railian.mobile.githubrepos.util.extensions.show
import com.railian.mobile.githubrepos.util.ui.LastItemVisibilityListener
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search),
    MviView<SearchAction, ReposListViewState> {

    override var currentState: ReposListViewState =
        ReposListViewState()

    override val actionFlow: MutableLiveData<SearchAction> = MutableLiveData()

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    @Inject
    lateinit var userDataStore: UserDataStore
    private lateinit var searchDataFlow: SearchDataFlow
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitHubReposApp.appComponent.getModule(SearchModule()).inject(this)
        searchDataFlow =
            ViewModelProviders.of(this, viewModelFactory).get(SearchDataFlow::class.java)
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
        searchDataFlow.bind(actionFlow)
        searchDataFlow.stateFlow.value = currentState
        prepareSearchField()
        prepareRecyclerView()

        if (!userDataStore.isUserLoggedIn) {
            val provider = OAuthProvider.newBuilder("github.com")
            val scopes = listOf("user:email")
            provider.setScopes(scopes)
            auth.startActivityForSignInWithProvider(activity!!, provider.build())
                .addOnSuccessListener {
                    // save user data in repository
                    println(it)
                    userDataStore.token = (it.credential as zzg).accessToken
                }
                .addOnFailureListener {
                    println(it)
                    // show error and block search
                }
        }

        searchDataFlow.stateFlow.observe(viewLifecycleOwner, Observer {
            renderOnNewState(it) {
                if (loading) {
                    searchDataFlow.repositoriesAdapter.showLoading()
                    return@renderOnNewState
                }

                if (repositories.isEmpty()) {
                    searchResults.hide()
                    emptyListLayout.show()
                } else {
                    emptyListLayout.hide()
                    searchResults.show()
                }

                if (error != null) {
                    //showError
                    return@renderOnNewState
                }

                if (repositoryUrl.isNotEmpty()) {
                    activity?.openUrlInBrowser(repositoryUrl)
                }
            }
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
                    actionFlow.value = SearchAction.SearchReposAction(
                        searchText.text.toString().trim()
                    )
                    false
                } else
                    return@setOnEditorActionListener true
            }
        }
    }

    private fun prepareRecyclerView() {
        searchResults.adapter = searchDataFlow.repositoriesAdapter
        searchResults.addOnScrollListener(object : LastItemVisibilityListener() {
            override fun onLastItemVisible() {
                if (searchDataFlow.stateFlow.value?.loading == false)
                    actionFlow.value = SearchAction.SearchReposAction(
                        searchText.text.toString().trim(),
                        isPaging = true
                    )
            }
        })
    }

}
