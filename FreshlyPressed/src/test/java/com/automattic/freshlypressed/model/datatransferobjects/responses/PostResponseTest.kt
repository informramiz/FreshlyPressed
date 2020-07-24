package com.automattic.freshlypressed.model.datatransferobjects.responses

import com.automattic.freshlypressed.extensions.createDummyPostResponse
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Ramiz Raja on 23/07/2020.
 */
class PostResponseTest {
    private val postResponse = createDummyPostResponse()

    @Test
    fun postResponseToPostEntity_containsSameData() {
        //WHEN: PostResponse is converted to Post
        val convertedPostEntity = postResponse.toPostEntity(1)

        //THEN: The data is same
        assertThat(convertedPostEntity.id , CoreMatchers.`is`(postResponse.id))
        assertThat(convertedPostEntity.siteId, CoreMatchers.`is`(postResponse.siteId))
        assertThat(convertedPostEntity.date, CoreMatchers.`is`(postResponse.parsedDate.time))
        assertThat(convertedPostEntity.title, CoreMatchers.`is`(postResponse.title))
        assertThat(convertedPostEntity.author.id, CoreMatchers.`is`(postResponse.author.id))
        assertThat(convertedPostEntity.author.url, CoreMatchers.`is`(postResponse.author.url))
        assertThat(convertedPostEntity.featuredImageUrl, CoreMatchers.`is`(postResponse.featuredImageUrl))
    }
}