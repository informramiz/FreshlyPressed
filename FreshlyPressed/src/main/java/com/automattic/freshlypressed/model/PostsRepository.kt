package com.automattic.freshlypressed.model

import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.automattic.freshlypressed.domainmodels.Post
import com.automattic.freshlypressed.domainmodels.toPost
import com.automattic.freshlypressed.model.db.daos.PostDao
import com.automattic.freshlypressed.model.utils.ApiResponse
import com.automattic.freshlypressed.model.utils.apiCallDirect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
class PostsRepository @Inject constructor(
    private val wordPressApi: WordPressPostsApi,
    private val postsDao: PostDao
) : IPostsRepository {
    override suspend fun fetchSubscriberCount(postId: Long, url: String) {
        val host = url.toUri().host ?: return
        val apiResponse = apiCallDirect { wordPressApi.getSubscriberCount(host) }
        if (apiResponse is ApiResponse.Success) {
            postsDao.updatePost(postId, apiResponse.data.subscriberCount)
        }
    }

    @ExperimentalPagingApi
    override fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(20),
            initialKey = 1,
            remoteMediator = PostsRemoteMediator(
                wordPressApi,
                postsDao
            ),
            pagingSourceFactory = { postsDao.getPostsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toPost() }
        }
    }
}