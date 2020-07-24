package com.automattic.freshlypressed.model.utils

import com.automattic.freshlypressed.extensions.exhaustive


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
sealed class OperationResponse<out T: Any> {
    sealed class Success<out T: Any>: OperationResponse<T>() {
        data class WithData<out T: Any>(val data: T): Success<T>()
        object Empty: Success<Nothing>()
    }
    class Failure(val error: Throwable): OperationResponse<Nothing>() {
        constructor(msg: String): this(Throwable(msg))
    }
}

fun <T: Any> ApiResponse<T>.toOperationResponse(): OperationResponse<T> {
    return when (this) {
        is ApiResponse.Success -> OperationResponse.Success.WithData(data)
        is ApiResponse.Error -> OperationResponse.Failure(error)
        is ApiResponse.Empty -> OperationResponse.Success.Empty
    }.exhaustive
}