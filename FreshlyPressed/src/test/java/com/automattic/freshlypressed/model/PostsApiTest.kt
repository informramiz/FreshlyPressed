package com.automattic.freshlypressed.model

import com.automattic.freshlypressed.model.PostsApi
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.Call
import okhttp3.OkHttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class PostsApiTest {
    @Mock
    lateinit var httpClient: OkHttpClient
    @Mock
    lateinit var call: Call
    private lateinit var postsApi: PostsApi

    @Before
    fun setUp() {
        postsApi = PostsApi(httpClient)
    }

    @Test
    fun loadSubscriberCountFails() {
        whenever(httpClient.newCall(any())).thenReturn(call)
        whenever(call.execute()).thenThrow(IOException("Call failed"))

        val response = postsApi.loadSubscribersCount("url.com")

        assertThat(response).isEqualTo(-1)
    }
}