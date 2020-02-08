package com.railian.mobile.githubrepos.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.railian.mobile.githubrepos.R

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var auth: FirebaseAuth

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

}
