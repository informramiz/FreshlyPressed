package com.automattic.freshlypressed.model.datatransferobjects.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
@JsonClass(generateAdapter = true)
data class SiteInfoResponse(
    @Json(name = "ID")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "subscribers_count")
    val subscriberCount: Long
)