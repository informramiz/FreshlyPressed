package com.automattic.freshlypressed.model.db.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.automattic.freshlypressed.model.db.entities.PostEntity


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("UPDATE posts SET subscriber_count = :subscriberCount WHERE id = :postId")
    suspend fun updatePost(postId: Long, subscriberCount: Long)

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostById(postId: Long): PostEntity?

    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM posts ORDER BY date DESC")
    fun getPostsPagingSource(): PagingSource<Int, PostEntity>

    @Query("DELETE FROM posts")
    suspend fun clearAll()

    @Transaction
    suspend fun refreshPosts(newPosts: List<PostEntity>) {
        clearAll()
        insertPosts(newPosts)
    }
}