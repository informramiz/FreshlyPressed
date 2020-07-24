package com.automattic.freshlypressed.model.db.entities

import androidx.room.ColumnInfo
import com.squareup.moshi.Json


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
data class AuthorEntity(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String
)