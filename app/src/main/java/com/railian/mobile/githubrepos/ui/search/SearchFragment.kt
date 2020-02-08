package com.railian.mobile.githubrepos.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search),
    MviView<SearchAction, ReposListViewState> {

    override var currentState: ReposListViewState =
        ReposListViewState.Empty

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

        val provider = OAuthProvider.newBuilder("github.com")
        val scopes = listOf("user:email")
        provider.setScopes(scopes)

        auth.startActivityForSignInWithProvider(activity!!, provider.build())
            .addOnSuccessListener {
                // save user data in repository
                println(it)
                userDataStore.token = (it.credential as zzg).accessToken
                if (savedInstanceState == null) {
                    actionFlow.value =
                        SearchAction.SearchReposAction("MVI", 1, SearchAction.DataSource.NETWORK)
                }
            }
            .addOnFailureListener {
                println(it)
                // show error and block search
            }

        searchDataFlow.stateFlow.observe(viewLifecycleOwner, Observer<ReposListViewState> {
            renderOnNewState(it) {
                when (it) {
                    is ReposListViewState.Empty -> {
                        println(it)
                    }
                    is ReposListViewState.Loading -> {
                        println(it)
                    }
                    is ReposListViewState.Data -> {
                        println(it)
                    }
                    is ReposListViewState.Error -> {
                        println(it)
                    }
                }
            }
        })
    }
}
