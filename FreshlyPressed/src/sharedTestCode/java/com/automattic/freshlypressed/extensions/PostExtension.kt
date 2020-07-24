package com.automattic.freshlypressed.extensions

import com.automattic.freshlypressed.domainmodels.Author
import com.automattic.freshlypressed.domainmodels.Post
import com.automattic.freshlypressed.model.datatransferobjects.responses.AuthorResponse
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostResponse
import java.util.*


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
fun createDummyPost(
    id: Long = System.currentTimeMillis(),
    siteId: Long = System.currentTimeMillis(),
    author: Author = createDummyAuthor(),
    date: Date = DATE,
    title: String = "RR-Post",
    url: String = DUMMY_URL,
    excerpt: String = DUMMY_SUMMARY,
    featuredImageUrl: String? = null,
    subscriberCount: Long = 0
): Post {
    return Post(id, siteId, author, date, title, url, excerpt, featuredImageUrl, subscriberCount)
}

fun createDummyAuthor(
    id: Long = System.currentTimeMillis(),
    name: String = "RR",
    url: String = DUMMY_URL
): Author {
    return Author(id, name, url)
}

fun createDummyPostResponse(
    id: Long = System.currentTimeMillis(),
    siteId: Long = System.currentTimeMillis(),
    author: AuthorResponse = createDummyAuthorResponse(),
    date: String = DATE_FORMATTED_UTC,
    title: String = "RR-Post",
    url: String = DUMMY_URL,
    excerpt: String = DUMMY_SUMMARY,
    featuredImageUrl: String? = null
): PostResponse {
    return PostResponse(id, siteId, author, date, title, url, excerpt, featuredImageUrl)
}

fun createDummyAuthorResponse(
    id: Long = System.currentTimeMillis(),
    name: String = "RR",
    url: String = DUMMY_URL
): AuthorResponse {
    return AuthorResponse(id, name, url)
}