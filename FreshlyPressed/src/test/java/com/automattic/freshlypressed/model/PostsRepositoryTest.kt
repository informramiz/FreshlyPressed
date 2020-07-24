package com.automattic.freshlypressed.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.room.withTransaction
import com.automattic.freshlypressed.domainmodels.Post
import com.automattic.freshlypressed.domainmodels.toPost
import com.automattic.freshlypressed.extensions.createDummyPostResponse
import com.automattic.freshlypressed.model.db.AppDatabase
import com.automattic.freshlypressed.model.db.daos.FakePostDao
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ramiz Raja on 23/07/2020.
 */
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class PostsRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postDao: FakePostDao
    private lateinit var wordPressPostsApi: FakeWordPressPostsApi
    private val remotePosts = listOf(createDummyPostResponse())
    private val domainPosts = remotePosts.map { it.toPost() }

    //GIVEN: subject under test
    private lateinit var postsRepository: PostsRepository

    @Before
    fun createRepository() = runBlocking {
        wordPressPostsApi = FakeWordPressPostsApi(remotePosts.toMutableList())
        postDao = FakePostDao()
        postsRepository = PostsRepository(wordPressPostsApi, postDao)
    }

    @Test
    fun getPosts_returnsCorrectPosts() = runBlocking {
        //WHEN: posts are retrieved from paging source
        postsRepository.getPosts().collect { pagingData ->
            val posts = mutableListOf<Post>()
            pagingData.map {
                posts.add(it)
            }

            //THEN: retrieved posts should be the remotePosts
            assertThat(posts, IsEqual(domainPosts))
        }
    }
}