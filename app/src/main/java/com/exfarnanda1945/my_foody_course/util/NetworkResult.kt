package com.exfarnanda1945.my_foody_course.util

sealed class NetworkResult<T>(
    val data: T? = null,
    val msg: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(msg: String,data: T? = null) : NetworkResult<T>(data, msg)
    class Loading<T>() : NetworkResult<T>()
}