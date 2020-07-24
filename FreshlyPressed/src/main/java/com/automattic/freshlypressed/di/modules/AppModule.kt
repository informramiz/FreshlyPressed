package com.automattic.freshlypressed.di.modules

import com.automattic.freshlypressed.model.IPostsRepository
import com.automattic.freshlypressed.model.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


/**
 * Created by Ramiz Raja on 24/07/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
interface AppModule {
    @Binds
    fun bindPostsRepository(postsRepository: PostsRepository): IPostsRepository
}