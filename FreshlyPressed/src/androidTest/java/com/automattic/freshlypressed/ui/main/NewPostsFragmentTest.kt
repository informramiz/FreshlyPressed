package com.automattic.freshlypressed.ui.main

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.automattic.freshlypressed.HiltTestRunner
import com.automattic.freshlypressed.R
import com.automattic.freshlypressed.extensions.createDummyPostResponse
import com.automattic.freshlypressed.launchFragmentInHiltContainer
import com.automattic.freshlypressed.model.FakePostRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Ramiz Raja on 24/07/2020.
 */
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NewPostsFragmentTest {
    private val posts = listOf(createDummyPostResponse())
    lateinit var postsRepository: FakePostRepository
    lateinit var viewModel: PostsViewModel

    @Before
    fun setupRepository() {
        postsRepository = FakePostRepository(posts.toMutableList())
        viewModel = PostsViewModel(postsRepository)
    }

    @Test
    fun savedPost_dispalyedInUi() {
        launchFragmentInHiltContainer<NewPostsFragment> {  }

        onView(withId(R.id.author_name))
            .check(matches(isDisplayed()))
            .check(matches(withText(posts[0].author.name)))

        onView(withId(R.id.title))
            .check(matches(isDisplayed()))
            .check(matches(withText(posts[0].title)))
    }
}