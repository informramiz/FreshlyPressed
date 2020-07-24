package com.automattic.freshlypressed.model

import com.automattic.freshlypressed.model.datatransferobjects.responses.PostsResponse
import com.automattic.freshlypressed.model.datatransferobjects.responses.SiteInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
interface WordPressPostsApi {
    @GET("discover.wordpress.com/posts")
    suspend fun getPosts(@Query("page") page: Int, @Query("number") itemCount: Int = 20): PostsResponse

    @GET("{siteHost}")
    suspend fun getSubscriberCount(@Path("siteHost") siteHost: String): SiteInfoResponse
}