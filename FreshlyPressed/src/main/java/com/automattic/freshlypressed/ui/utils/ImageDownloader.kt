package com.automattic.freshlypressed.ui.utils

import android.widget.ImageView
import com.bumptech.glide.Glide


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
object ImageDownloader {
    fun download(url: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
}