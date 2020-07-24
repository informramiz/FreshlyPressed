package com.automattic.freshlypressed.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.automattic.freshlypressed.model.db.daos.PostDao
import com.automattic.freshlypressed.model.db.entities.PostEntity


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
@Database(entities = [PostEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract val postDao: PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context, AppDatabase::class.java, "posts_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}