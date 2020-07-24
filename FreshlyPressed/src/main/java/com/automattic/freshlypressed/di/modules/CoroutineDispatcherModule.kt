package com.automattic.freshlypressed.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
object CoroutineDispatcherModule {
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}