package com.example.androidsamplekotlinnoncompose.sealed

sealed class ResponseState<T>(
    val resData: T? = null,
    val resErrorMessage: String? = null
) {
    //is Loading
    class Loading<T> : ResponseState<T>()

    //Res Success
    class ResSuccess<T>(data: T?) : ResponseState<T>(resData = data)

    //Res Error
    class ResFailure<T>(errorMessage: String?) : ResponseState<T>(resErrorMessage = errorMessage)
}