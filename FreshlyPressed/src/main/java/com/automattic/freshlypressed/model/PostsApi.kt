package com.automattic.freshlypressed.model

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PostsApi(private var mClient: OkHttpClient?) {
    fun loadSubscribersCount(url: String): Int {
        val request = Request.Builder()
                .url("https://public-api.wordpress.com/rest/v1.1/sites/$url")
                .build()
        try {
            val response: Response = mClient!!.newCall(request).execute()
            val json = JSONObject(response.body!!.string())
            return json.getInt("subscribers_count")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return -1
    }

    fun loadPosts(): JSONArray {
        val request = Request.Builder()
                .url("https://public-api.wordpress.com/rest/v1.1/sites/discover.wordpress.com/posts?number=10")
                .build()

        val response = mClient!!.newCall(request).execute()

        val json = JSONObject(response.body!!.string())
        return json.getJSONArray("posts")
    }
}
