package com.automattic.freshlypressed.model.db.daos

import androidx.paging.PagingSource
import com.automattic.freshlypressed.model.db.entities.PostEntity


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class FakePostsPagingSource : PagingSource<Int, PostEntity>() {
    var triggerError = false
    var posts: List<PostEntity> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        if (triggerError) {
            return LoadResult.Error(Exception("A test error triggered"))
        }

        return LoadResult.Page(
            data = posts,
            prevKey = null,
            nextKey = null
        )
    }
}