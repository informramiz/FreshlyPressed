package com.automattic.freshlypressed.model.datatransferobjects.responses

import com.automattic.freshlypressed.model.db.entities.AuthorEntity
import com.automattic.freshlypressed.model.db.entities.PostEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
@JsonClass(generateAdapter = true)
data class PostResponse(
    @Json(name = "ID")
    val id: Long,
    @Json(name = "site_ID")
    val siteId: Long,
    @Json(name = "author")
    val author: AuthorResponse,
    @Json(name = "date")
    val date: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "URL")
    val url: String,
    @Json(name = "excerpt")
    val excerpt: String,
    @Json(name = "featured_image")
    val featuredImageUrl: String? = null
) {
    val parsedDate: Date = DATE_FORMATTER.parse(date) ?: Calendar.getInstance().time
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        val DATE_FORMATTER = SimpleDateFormat(DATE_FORMAT, Locale.US)
    }
}

fun PostResponse.toPostEntity(page: Int): PostEntity {
    return PostEntity(id, siteId, author.toAuthorEntity(), parsedDate.time, title, url, excerpt, featuredImageUrl, page = page)
}

fun AuthorResponse.toAuthorEntity(): AuthorEntity {
    return AuthorEntity(id, name, url)
}