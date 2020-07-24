package com.automattic.freshlypressed.model.db.daos

import androidx.paging.PagingSource
import com.automattic.freshlypressed.model.db.entities.PostEntity


/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class FakePostDao(val posts: MutableList<PostEntity> = mutableListOf<PostEntity>()) : PostDao {
    val pagingSource = FakePostsPagingSource()

    override suspend fun insertPosts(posts: List<PostEntity>) {
        this.posts.addAll(posts)
        updatePagingSource()
    }

    override suspend fun updatePost(post: PostEntity) {
        onValidPost(post.id) {
            posts[it] = post
            updatePagingSource()
        }
    }

    private fun onValidPost(postId: Long, block: (index: Int) -> Unit): Boolean {
        val index = posts.indexOfFirst { it.id == postId }
        if (index != -1) {
            block(index)
            return true
        }

        return false
    }

    override suspend fun updatePost(postId: Long, subscriberCount: Long) {
        onValidPost(postId) {
            posts[it] = posts[it].copy(subscriberCount = subscriberCount)
            updatePagingSource()
        }
    }

    override suspend fun getPostById(postId: Long): PostEntity? {
        val index = posts.indexOfFirst { it.id == postId }
        return if (index != -1) {
            posts[index]
        } else {
            null
        }
    }

    override suspend fun getPosts(): List<PostEntity> {
        return posts
    }

    override fun getPostsPagingSource(): PagingSource<Int, PostEntity> {
        return pagingSource
    }

    override suspend fun clearAll() {
        posts.clear()
        updatePagingSource()
    }

    private fun updatePagingSource() {
        pagingSource.posts = posts
    }
}