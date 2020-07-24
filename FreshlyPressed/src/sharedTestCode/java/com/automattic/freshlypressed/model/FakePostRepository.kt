package com.automattic.freshlypressed.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.automattic.freshlypressed.domainmodels.Post
import com.automattic.freshlypressed.domainmodels.toPost
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostResponse
import com.automattic.freshlypressed.model.db.daos.FakePostDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class FakePostRepository(val posts: MutableList<PostResponse> = mutableListOf()) : IPostsRepository {
    val fakePostDao = FakePostDao()
    val fakeWordPressPostsApi = FakeWordPressPostsApi(posts)

    override suspend fun fetchSubscriberCount(postId: Long, url: String) {
        fakePostDao.updatePost(postId, fakeWordPressPostsApi.getSubscriberCount("").subscriberCount)
    }

    @ExperimentalPagingApi
    override fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { fakePostDao.getPostsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toPost() }
        }
    }
}