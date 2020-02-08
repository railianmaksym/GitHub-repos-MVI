package com.railian.mobile.githubrepos.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.railian.mobile.githubrepos.R
import com.railian.mobile.githubrepos.ui.base.MviView

class SearchFragment : Fragment(R.layout.fragment_search),
    MviView<SearchAction, ReposListViewState> {

    override var currentState: ReposListViewState =
        ReposListViewState()

    override val action: MutableLiveData<SearchAction> = MutableLiveData()

    private lateinit var auth: FirebaseAuth
    private val viewModel = SearchViewModel()

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
        viewModel.bind(action)

        val provider = OAuthProvider.newBuilder("github.com")
        val scopes = listOf("user:email")
        provider.setScopes(scopes)

        auth.startActivityForSignInWithProvider(activity!!, provider.build())
            .addOnSuccessListener {
                // save user data in repository
                println(it)
            }
            .addOnFailureListener {
                println(it)
                // show error and block search
            }

        viewModel.state.observe(viewLifecycleOwner, Observer<ReposListViewState> {
            renderOnNewState(it) {
                println("")
            }
        })

        if (savedInstanceState == null) {
            action.value =
                SearchAction.SearchReposAction("MVI", SearchAction.DataSource.NETWORK)
        }
    }
}
