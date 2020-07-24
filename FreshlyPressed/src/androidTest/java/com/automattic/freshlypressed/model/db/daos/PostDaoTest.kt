package com.automattic.freshlypressed.model.db.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.automattic.freshlypressed.extensions.createDummyPostEntity
import com.automattic.freshlypressed.model.db.AppDatabase
import com.automattic.freshlypressed.model.db.entities.PostEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by Ramiz Raja on 23/07/2020.
 */

@SmallTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PostDaoTest {

    /**
     * swaps the background executor used by the Architecture Components with a
     * different one which executes each task synchronously
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * GIVEN: Subject under test
     */
    lateinit var appDatabase: AppDatabase
    lateinit var postDao: PostDao
    private val post = createDummyPostEntity()

    @Before
    fun setupDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
            .build()
        postDao = appDatabase.postDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun insertPostAndGetById_returnsSamePost() = runBlockingTest {
        //WHEN: A post is inserted and then retrieved
        postDao.insertPosts(listOf(post))
        val loadedPost = postDao.getPostById(post.id)

        //THEN: it should have same data as before insertion
        assertThat(loadedPost as PostEntity, notNullValue())
        assertThat(loadedPost, IsEqual(post))
    }

    @Test
    fun updatePostAndGetById_returnsSamePost() = runBlockingTest {
        //WHEN: A post is inserted, updated and then retrieved
        postDao.insertPosts(listOf(post))
        val updatedPost = post.copy(title = "my new Title", subscriberCount = 5)
        postDao.updatePost(updatedPost)
        val loadedPost = postDao.getPostById(post.id)

        //THEN: it should have same data as before insertion
        assertThat(loadedPost as PostEntity, notNullValue())
        assertThat(loadedPost, IsEqual(updatedPost))
    }

    @Test
    fun insertPostAndGetAllPosts_returnsOnePost() = runBlockingTest {
        //WHEN: A single post is inserted and retried list of all posts
        postDao.insertPosts(listOf(post))

        val posts = postDao.getPosts()
        //THEN: retrieved list of posts should be 1
        assertThat(posts.size, IsEqual(1))
        assertThat(posts, IsEqual(listOf(post)))
    }

    @Test
    fun insertPostsAndGetAllPosts_returnsSamePosts() = runBlockingTest {
        //WHEN: 2 posts are inserted and then retrieved
        val post2 = post.copy(id = System.currentTimeMillis())
        val insertedPosts = listOf(post, post2)
        postDao.insertPosts(insertedPosts)

        val loadedPosts = postDao.getPosts()
        //THEN: Retrieved list of posts should be same as the inserted one
        assertThat(loadedPosts, IsEqual(insertedPosts))
    }
}