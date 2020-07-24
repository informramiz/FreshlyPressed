package com.automattic.freshlypressed.extensions

import com.automattic.freshlypressed.model.db.entities.AuthorEntity
import com.automattic.freshlypressed.model.db.entities.PostEntity


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
fun createDummyPostEntity(
    id: Long = System.currentTimeMillis(),
    siteId: Long = System.currentTimeMillis(),
    author: AuthorEntity = createDummyAuthorEntity(),
    date: Long = DATE.time,
    title: String = "RR-Post",
    url: String = DUMMY_URL,
    excerpt: String = DUMMY_SUMMARY,
    featuredImageUrl: String? = null,
    subscriberCount: Long = 0
): PostEntity {
    return PostEntity(id, siteId, author, date, title, url, excerpt, featuredImageUrl, subscriberCount)
}

fun createDummyAuthorEntity(
    id: Long = System.currentTimeMillis(),
    name: String = "RR",
    url: String = DUMMY_URL
): AuthorEntity {
    return AuthorEntity(id, name, url)
}