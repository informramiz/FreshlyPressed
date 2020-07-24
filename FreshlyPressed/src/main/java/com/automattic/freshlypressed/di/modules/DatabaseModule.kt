package com.automattic.freshlypressed.di.modules

import android.content.Context
import com.automattic.freshlypressed.model.db.AppDatabase
import com.automattic.freshlypressed.model.db.daos.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePostsDao(appDatabase: AppDatabase) = appDatabase.postDao
}