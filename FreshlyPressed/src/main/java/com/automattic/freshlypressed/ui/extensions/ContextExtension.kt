package com.automattic.freshlypressed.ui.extensions

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
@ColorInt
fun Context.getSupportColor(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}