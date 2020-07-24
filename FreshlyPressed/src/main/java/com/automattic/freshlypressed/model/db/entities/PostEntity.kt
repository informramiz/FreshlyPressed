package com.automattic.freshlypressed.model.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "site_id")
    val siteId: Long,
    @Embedded(prefix = "author_")
    val author: AuthorEntity,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "excerpt")
    val excerpt: String,
    @ColumnInfo(name = "featured_image_url")
    val featuredImageUrl: String? = null,
    @ColumnInfo(name = "subscriber_count", defaultValue = "0")
    val subscriberCount: Long = 0,
    @ColumnInfo(name = "page")
    val page: Int = 1
)