package com.yms.domain.utils

sealed interface RootResult<out R> {
    data class Success<out R>(val data: R?) : RootResult<R>
    data class Error(val message:String) : RootResult<Nothing>
    data object Loading : RootResult<Nothing>
}