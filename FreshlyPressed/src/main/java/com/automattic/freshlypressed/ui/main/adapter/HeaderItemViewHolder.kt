package com.automattic.freshlypressed.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.automattic.freshlypressed.databinding.PostListFragmentHeaderItemBinding
import com.automattic.freshlypressed.ui.main.PostUiModel


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
class HeaderItemViewHolder(private val viewBinding: PostListFragmentHeaderItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(header: PostUiModel.HeaderItem) {
        viewBinding.header.text = header.description
    }

    companion object {
        fun create(parent: ViewGroup): HeaderItemViewHolder {
            return PostListFragmentHeaderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).let { HeaderItemViewHolder(it) }
        }
    }
}