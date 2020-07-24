package com.automattic.freshlypressed.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.automattic.freshlypressed.domainmodels.Post
import kotlinx.coroutines.flow.Flow


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
interface IPostsRepository {
    suspend fun fetchSubscriberCount(postId: Long, url: String)

    @ExperimentalPagingApi
    fun getPosts(): Flow<PagingData<Post>>
}