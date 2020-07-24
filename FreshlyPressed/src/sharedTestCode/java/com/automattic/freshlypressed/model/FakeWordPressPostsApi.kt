package com.automattic.freshlypressed.model

import com.automattic.freshlypressed.extensions.DATE
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostResponse
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostsResponse
import com.automattic.freshlypressed.model.datatransferobjects.responses.SiteInfoResponse


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class FakeWordPressPostsApi(val posts: MutableList<PostResponse> = mutableListOf()) : WordPressPostsApi {
    companion object {
        const val SUBSCRIBER_COUNT = 2L
        const val AUTHOR_NAME = "RR"
    }
    override suspend fun getPosts(page: Int, itemCount: Int): PostsResponse {
        return PostsResponse(posts.size.toLong(), posts)
    }

    override suspend fun getSubscriberCount(siteHost: String): SiteInfoResponse {
        return SiteInfoResponse(DATE.time, "RR", SUBSCRIBER_COUNT)
    }

}