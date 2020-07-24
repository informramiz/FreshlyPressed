package com.automattic.freshlypressed.extensions

import com.automattic.freshlypressed.domainmodels.Post
import java.util.*


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
const val DUMMY_URL = "https://www.google.com"
const val DUMMY_SUMMARY = "Hello world, here I am, writing tests for something that is not tested"
val DATE: Date = Calendar.getInstance().time
val DATE_FORMATTED_UTC: String = Post.DATE_FORMATTER.format(DATE)