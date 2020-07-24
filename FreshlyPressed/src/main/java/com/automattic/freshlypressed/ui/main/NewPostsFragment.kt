package com.automattic.freshlypressed.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.automattic.freshlypressed.databinding.PostsFragmentBinding
import com.automattic.freshlypressed.ui.adapters.AppLoadStateAdapter
import com.automattic.freshlypressed.ui.main.adapter.OnPostItemClickListener
import com.automattic.freshlypressed.ui.main.adapter.PostsPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPagingApi
@AndroidEntryPoint
class NewPostsFragment : Fragment() {

    private val viewModel: PostsViewModel by viewModels()
    private lateinit var viewBinding: PostsFragmentBinding
    private val pagingDataAdapter = PostsPagingAdapter(OnPostItemClickListener { post ->
        //TODO: replace it with chrome custom tabs
        startActivity(Intent(Intent.ACTION_VIEW, post.url.toUri()))
    }).apply {
        fetchSubscriberCount = { postId, authorUrl ->
            viewModel.fetchSubscriberCount(postId, authorUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return PostsFragmentBinding.inflate(inflater, container, false)
            .also { viewBinding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.postsList.adapter = pagingDataAdapter.withLoadStateFooter(AppLoadStateAdapter { pagingDataAdapter.retry() })

        lifecycleScope.launch {
            viewModel.postsFlow.collectLatest {
                pagingDataAdapter.submitData(it)
            }
        }

        pagingDataAdapter.addLoadStateListener {
            viewBinding.progressBar.isVisible = it.refresh is LoadState.Loading ||
                    (it.append is LoadState.Loading && pagingDataAdapter.itemCount == 0)
            checkForError(it)
        }
    }

    private fun checkForError(loadState: CombinedLoadStates) {
        // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.append as? LoadState.Error
        errorState?.let {
            Toast.makeText(
                requireContext(),
                it.error.localizedMessage,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}