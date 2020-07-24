package com.automattic.freshlypressed.ui.main.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.automattic.freshlypressed.R
import com.automattic.freshlypressed.ui.main.PostUiModel


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
class PostsPagingAdapter(private val onPostItemClickListener: OnPostItemClickListener) :
    PagingDataAdapter<PostUiModel, RecyclerView.ViewHolder>(PostUiModel.DIFF_ITEM_CALLBACK) {
    var fetchSubscriberCount: ((postId: Long, authorUrl: String) -> Unit)? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        when (item) {
            is PostUiModel.PostItem -> {
                if (item.post.subscriberCount == 0L) {
                    fetchSubscriberCount?.invoke(item.post.id, item.post.author.url)
                }
                (holder as PostItemViewHolder).bind(item, onPostItemClickListener)
            }
            is PostUiModel.HeaderItem -> (holder as HeaderItemViewHolder).bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.post_list_fragment_header_item -> HeaderItemViewHolder.create(parent)
            else -> PostItemViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PostUiModel.PostItem -> R.layout.post_list_fragment_item
            is PostUiModel.HeaderItem -> R.layout.post_list_fragment_header_item
            null -> throw UnsupportedOperationException("Unknown model type provided")
        }
    }
}