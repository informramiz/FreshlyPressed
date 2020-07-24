package com.automattic.freshlypressed.model

import androidx.paging.PagingSource
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostResponse
import com.automattic.freshlypressed.model.utils.ApiResponse
import com.automattic.freshlypressed.model.utils.apiCallDirect


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
private const val START_PAGE = 1
class PostsPagingDataSource(private val wordPressPostsApi: WordPressPostsApi) : PagingSource<Int, PostResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostResponse> {
        val currentPage = params.key ?: START_PAGE
        return when (val apiResponse = apiCallDirect { wordPressPostsApi.getPosts(currentPage, params.loadSize) }) {
            is ApiResponse.Success -> {
                LoadResult.Page(
                    data = apiResponse.data.posts,
                    prevKey = null,
                    nextKey = if (apiResponse.data.posts.isEmpty()) null else currentPage + 1
                )
            }
            ApiResponse.Empty -> {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey =  null
                )
            }
            is ApiResponse.Error -> LoadResult.Error(apiResponse.error)
        }
    }

}