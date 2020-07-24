package com.automattic.freshlypressed.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.automattic.freshlypressed.databinding.LoadStateListItemBinding


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
class LoadStateViewHolder(
        private val viewBinding: LoadStateListItemBinding,
        private val retry: () -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {
    init {
        viewBinding.retryButton.setOnClickListener { retry() }
    }
    fun bind(loadState: LoadState) {
        viewBinding.progressBar.isVisible = loadState is LoadState.Loading
        viewBinding.retryButton.isVisible = loadState is LoadState.Error
        viewBinding.errorMsg.isVisible = loadState is LoadState.Error
        if (loadState is LoadState.Error) {
            viewBinding.errorMsg.text = loadState.error.localizedMessage
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            return LoadStateViewHolder(
                LoadStateListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                retry
            )
        }
    }
}