package com.automattic.freshlypressed.domainmodels

import androidx.recyclerview.widget.DiffUtil
import com.automattic.freshlypressed.model.datatransferobjects.responses.AuthorResponse
import com.automattic.freshlypressed.model.datatransferobjects.responses.PostResponse
import com.automattic.freshlypressed.model.db.entities.AuthorEntity
import com.automattic.freshlypressed.model.db.entities.PostEntity
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
data class Post(
    val id: Long,
    val siteId: Long,
    val author: Author,
    val date: Date,
    val title: String,
    val url: String,
    val excerpt: String,
    val featuredImageUrl: String? = null,
    val subscriberCount: Long = 0
) {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        val DATE_FORMATTER = SimpleDateFormat(DATE_FORMAT, Locale.US)
        val DIFF_ITEM_CALL = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

        }
    }
}

fun PostResponse.toPost(): Post {
    return Post(id, siteId, author.toAuthor(), parsedDate, title, url, excerpt, featuredImageUrl)
}

fun PostEntity.toPost(): Post {
    return Post(id, siteId, author.toAuthor(), Date(date), title, url, excerpt, featuredImageUrl, subscriberCount)
}

fun AuthorEntity.toAuthor(): Author {
    return Author(id, name, url)
}

fun AuthorResponse.toAuthor(): Author {
    return Author(id, name, url)
}