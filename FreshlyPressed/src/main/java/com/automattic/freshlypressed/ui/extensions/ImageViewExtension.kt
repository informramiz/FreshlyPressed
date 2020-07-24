package com.automattic.freshlypressed.ui.extensions

import android.widget.ImageView
import com.automattic.freshlypressed.ui.utils.ImageDownloader


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
fun ImageView.load(url: String?) {
    url ?: return
    ImageDownloader.download(url, this)
}