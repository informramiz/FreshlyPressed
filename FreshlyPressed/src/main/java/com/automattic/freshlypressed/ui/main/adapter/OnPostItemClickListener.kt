package com.automattic.freshlypressed.ui.main.adapter

import com.automattic.freshlypressed.domainmodels.Post


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class OnPostItemClickListener(val callback: (post: Post) -> Unit) {
    fun onClick(post: Post) = callback(post)
}