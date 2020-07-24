package com.automattic.freshlypressed.domainmodels

import com.automattic.freshlypressed.extensions.DATE
import com.automattic.freshlypressed.extensions.createDummyPost
import com.automattic.freshlypressed.extensions.createDummyPostEntity
import com.automattic.freshlypressed.extensions.createDummyPostResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.lessThan
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class PostTest {
    //GIVEN:
    private val post = createDummyPost()
    private val postResponse = createDummyPostResponse()
    private val postEntity = createDummyPostEntity()

    @Test
    fun parsedDate_containsCorrectDate() {
        //WHEN: the date is parsed inside parsedDate field of Post
        val parsedInsidePost = post.date

        //THEN: It should be parsed correctly
        assertThat(parsedInsidePost.time, allOf(greaterThan(DATE.time - 500), lessThan(DATE.time + 500)))
    }

    @Test
    fun fromPostResponseToPost_containsSameData() {
        //WHEN: PostResponse is converted to Post
        val convertedPost = postResponse.toPost()

        //THEN: The data is same
        assertThat(convertedPost.id , `is`(postResponse.id))
        assertThat(convertedPost.siteId, `is`(postResponse.siteId))
        assertThat(convertedPost.date, `is`(postResponse.parsedDate))
        assertThat(convertedPost.title, `is`(postResponse.title))
        assertThat(convertedPost.author.id, `is`(postResponse.author.id))
        assertThat(convertedPost.author.url, `is`(postResponse.author.url))
        assertThat(convertedPost.featuredImageUrl, `is`(postResponse.featuredImageUrl))
    }

    @Test
    fun fromPostEntityToPost_containsSameData() {
        //WHEN: PostResponse is converted to Post
        val convertedPost = postEntity.toPost()

        //THEN: The data is same
        assertThat(convertedPost.id , `is`(postEntity.id))
        assertThat(convertedPost.siteId, `is`(postEntity.siteId))
        assertThat(convertedPost.date.time, `is`(postEntity.date))
        assertThat(convertedPost.title, `is`(postEntity.title))
        assertThat(convertedPost.author.id, `is`(postEntity.author.id))
        assertThat(convertedPost.author.url, `is`(postEntity.author.url))
        assertThat(convertedPost.featuredImageUrl, `is`(postEntity.featuredImageUrl))
    }
}