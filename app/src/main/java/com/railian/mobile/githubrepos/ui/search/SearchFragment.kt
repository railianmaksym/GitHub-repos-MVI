package com.railian.mobile.githubrepos.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.railian.mobile.githubrepos.R
import com.railian.mobile.githubrepos.ui.base.MviView
import com.railian.mobile.githubrepos.ui.base.ReposListViewState

class SearchFragment : Fragment(R.layout.fragment_search),
    MviView<SearchAction, ReposListViewState> {

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

        viewModel.state.observe(viewLifecycleOwner, Observer<ReposListViewState> {
            println("")
        })

        action.value = SearchAction.SearchReposAction("MVI")
//        val provider = OAuthProvider.newBuilder("github.com")
//        val scopes = listOf("user:email")
//        provider.setScopes(scopes)
//
//        auth.startActivityForSignInWithProvider(activity!!, provider.build())
//            .addOnSuccessListener {
//                // save user data in repository
//                println(it)
//            }
//            .addOnFailureListener {
//                println(it)
//                // show error and block search
//            }
//
//        auth.signOut()
    }

    override fun render(state: ReposListViewState) {

    }

}
