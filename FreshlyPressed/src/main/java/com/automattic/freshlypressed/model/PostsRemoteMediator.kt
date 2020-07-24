package com.automattic.freshlypressed.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.automattic.freshlypressed.extensions.exhaustive
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostResponse
import com.automattic.freshlypressed.model.datatransferobjects.responses.toPostEntity
import com.automattic.freshlypressed.model.db.AppDatabase
import com.automattic.freshlypressed.model.db.daos.PostDao
import com.automattic.freshlypressed.model.db.entities.PostEntity
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
private const val START_PAGE = 1
@ExperimentalPagingApi
class PostsRemoteMediator(
    private val wordPressPostsApi: WordPressPostsApi,
    private val postsDao: PostDao
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        val currentPageToLoad = getKey(loadType, state) ?: return MediatorResult.Success(endOfPaginationReached = true)

        Timber.v("page=, currentPageToLoad=$currentPageToLoad, loadType=$loadType")

        return try {
            val postsResponse = wordPressPostsApi.getPosts(currentPageToLoad, state.config.pageSize)
            val endOfPagination = postsResponse.posts.isEmpty()
            Timber.v("page=, items returned=${postsResponse.posts.size}")
            saveData(postsResponse.posts, loadType == LoadType.REFRESH, currentPageToLoad)
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getKey(loadType: LoadType, state: PagingState<Int, PostEntity>): Int? {
        return when(loadType) {
            LoadType.PREPEND -> null
            LoadType.REFRESH -> START_PAGE
            LoadType.APPEND -> {
                state.lastItemOrNull()?.page?.plus(1) ?: START_PAGE
            }
        }.exhaustive
    }

    private suspend fun saveData(posts: List<PostResponse>,
                                 shouldClearExistingData: Boolean,
                                 page: Int) {
        if (shouldClearExistingData) {
            postsDao.refreshPosts(posts.map { it.toPostEntity(page) })
        } else {
            postsDao.insertPosts(posts.map { it.toPostEntity(page) })
        }
    }
}