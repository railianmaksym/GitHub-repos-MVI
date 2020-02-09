package com.railian.mobile.githubrepos.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.railian.mobile.githubrepos.R
import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_github_repository.*

class GitHubReposAdapter(val onItemClicked: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val REPOSITORY_VIEW_TYPE = 1
        private const val LOADING_VIEW_TYPE = 2
    }

    var repositories: List<GitHubRepository?> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == REPOSITORY_VIEW_TYPE) {
            val view = inflater.inflate(R.layout.item_github_repository, parent, false)
            RepositoryViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryViewHolder) {
            val repository = repositories[position]!!

            with(holder) {
                repositoryName.text = repository.fullName
                programmingLanguage.text = repository.language
                repositoryDescription.text = repository.description
                starsCount.text = repository.starsCountString
                forksCount.text = repository.forksCountString
                repositoryLayout.setOnClickListener {
                    onItemClicked(repository.htmlUrl)
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (repositories[position] == null)
            LOADING_VIEW_TYPE
        else
            REPOSITORY_VIEW_TYPE
    }

    fun showLoading() {
        (repositories as MutableList).add(null)
        notifyItemInserted(repositories.lastIndex)
    }

    fun addItems(items: List<GitHubRepository>) {
        val lastPosition = repositories.lastIndex
        repositories = repositories.filterNotNull()
        (repositories as MutableList).addAll(items)
        notifyItemChanged(lastPosition)
    }

    internal class RepositoryViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer

    internal class LoadingViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer
}
