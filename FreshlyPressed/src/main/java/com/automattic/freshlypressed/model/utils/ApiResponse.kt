package com.automattic.freshlypressed.model.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
sealed class ApiResponse<out T: Any> {
    companion object {
        fun <T: Any> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                if (response.body() != null) {
                    Success(response.body()!!)
                } else {
                    Empty
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Error(Exception(errorBody ?: "Unknown error occurred"))
            }
        }

        fun create(throwable: Throwable): ApiResponse.Error {
            return Error(throwable)
        }
    }

    class Success<out T: Any>(val data: T) : ApiResponse<T>()
    object Empty : ApiResponse<Nothing>()
    class Error(val error: Throwable) : ApiResponse<Nothing>()
}

suspend fun <T: Any> apiCall(block: suspend () -> Response<T>): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponse.create(block())
        } catch (e: Exception) {
            Timber.e(e)
            ApiResponse.create(e)
        }
    }
}

suspend fun <T: Any> apiCallDirect(block: suspend () -> T): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponse.Success(block())
        } catch (e: Exception) {
            Timber.e(e)
            ApiResponse.create(e)
        }
    }
}