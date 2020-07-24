package com.automattic.freshlypressed.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.automattic.freshlypressed.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_activity)
    }
}
