package com.automattic.freshlypressed.model.datatransferobjects.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
@JsonClass(generateAdapter = true)
data class PostsResponse(
    @Json(name = "found")
    val totalPosts: Long,
    @Json(name = "posts")
    val posts: List<PostResponse>
)