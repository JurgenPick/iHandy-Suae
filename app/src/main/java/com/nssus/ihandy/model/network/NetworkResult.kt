package com.nssus.ihandy.model.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T?, val statusCode: String) : NetworkResult<T>()
    data class Error<out T>(val errorMessage: String?, val statusCode: String) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>()
}