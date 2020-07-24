package com.automattic.freshlypressed.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.automattic.freshlypressed.R
import com.automattic.freshlypressed.databinding.PostListFragmentItemBinding
import com.automattic.freshlypressed.ui.extensions.getSupportColor
import com.automattic.freshlypressed.ui.extensions.load
import com.automattic.freshlypressed.ui.main.PostUiModel


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
class PostItemViewHolder(
    private val viewBinding: PostListFragmentItemBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(postItem: PostUiModel.PostItem, postItemClickListener: OnPostItemClickListener) {
        val post = postItem.post
        viewBinding.title.text = post.title
        val titleColor = if (post.featuredImageUrl.isNullOrBlank()) {
            viewBinding.title.context.getSupportColor(R.color.post_title_color_dark)
        } else {
            viewBinding.title.context.getSupportColor(R.color.post_title_color_light)
        }
        viewBinding.title.setTextColor(titleColor)
        viewBinding.summary.text = HtmlCompat.fromHtml(post.excerpt, HtmlCompat.FROM_HTML_MODE_COMPACT)
        viewBinding.authorName.text = post.author.name
        viewBinding.featuredImage.isVisible = post.featuredImageUrl != null
        viewBinding.featuredImage.load(post.featuredImageUrl)
        viewBinding.subscribersCount.text = String.format("%d", post.subscriberCount)

        viewBinding.root.setOnClickListener { postItemClickListener.onClick(post) }
    }

    companion object {
        fun create(parent: ViewGroup): PostItemViewHolder {
            return PostItemViewHolder(
                PostListFragmentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}