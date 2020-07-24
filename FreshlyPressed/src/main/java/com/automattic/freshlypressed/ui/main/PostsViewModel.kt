package com.automattic.freshlypressed.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.recyclerview.widget.DiffUtil
import com.automattic.freshlypressed.domainmodels.Post
import com.automattic.freshlypressed.model.IPostsRepository
import com.automattic.freshlypressed.model.PostsRepository
import com.automattic.freshlypressed.ui.extensions.isDayDifferent
import com.automattic.freshlypressed.ui.utils.PostUtils
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class PostsViewModel @ViewModelInject constructor(private val postsRepository: IPostsRepository): ViewModel() {
    val postsFlow = postsRepository.getPosts()
        .map { pagingData -> pagingData.map { PostUiModel.PostItem(it) } }
        .map {
            it.insertSeparators<PostUiModel.PostItem, PostUiModel> { firstPost, secondPost ->
                getHeader(firstPost, secondPost)
            }
        }
        .cachedIn(viewModelScope)

    private fun getHeader(firstPost: PostUiModel.PostItem?, secondPost: PostUiModel.PostItem?): PostUiModel? {
        return when {
            firstPost == null -> PostUiModel.HeaderItem(secondPost?.formattedDate ?: "")
            secondPost == null -> null
            else -> {
                if (firstPost.post.date.isDayDifferent(secondPost.post.date)) {
                    PostUiModel.HeaderItem(secondPost.formattedDate)
                } else {
                    null
                }
            }
        }
    }

    fun fetchSubscriberCount(postId: Long, url: String) {
        viewModelScope.launch { postsRepository.fetchSubscriberCount(postId, url) }
    }
}

sealed class PostUiModel {
    data class PostItem(val post: Post) : PostUiModel()
    data class HeaderItem(val description: String): PostUiModel()

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<PostUiModel>() {
            override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
                return (oldItem is PostItem && newItem is PostItem && oldItem.post.id == newItem.post.id)
                        || (oldItem is HeaderItem && newItem is HeaderItem && oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}

val PostUiModel.PostItem.formattedDate: String
    get() = PostUtils.printDate(post.date)